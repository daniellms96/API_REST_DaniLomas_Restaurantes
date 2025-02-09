package com.example.api_rest_danilomas_restaurantes.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marca esta clase como un componente de Spring para que pueda ser detectada automáticamente.
public class JwtFilter extends OncePerRequestFilter { // Hereda de OncePerRequestFilter para garantizar que el filtro se ejecute una sola vez por solicitud.

    private final JwtTokenProvider tokenProvider; // Proveedor de tokens JWT para validación y extracción de datos.
    private final UserDetailsService userDetailsService; // Servicio para cargar los detalles del usuario.

    // Constructor que inyecta las dependencias necesarias.
    public JwtFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extrae el token de la solicitud.
        String token = this.extractToken(request);

        // Verifica si el token es válido antes de procesarlo.
        if(this.tokenProvider.isValidToken(token)){
            // Obtiene el nombre de usuario a partir del token.
            String username = this.tokenProvider.getUsernameFromToken(token);

            // Carga los detalles del usuario desde el servicio correspondiente.
            UserDetails user = this.userDetailsService.loadUserByUsername(username);

            // Crea una autenticación basada en los detalles del usuario.
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), // Nombre de usuario autenticado.
                    user.getPassword(), // Contraseña.
                    user.getAuthorities()); // Roles o permisos del usuario.

            // Establece la autenticación en el contexto de seguridad de Spring.
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Continúa con el resto de la cadena de filtros.
        filterChain.doFilter(request, response);
    }

    // Método privado para extraer el token JWT del encabezado Authorization.
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // Obtiene el encabezado Authorization.
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // Verifica si el token tiene el prefijo "Bearer ".
            return bearerToken.substring(7); // Devuelve el token sin el prefijo "Bearer ".
        }
        return null; // Retorna null si no se encuentra un token válido.
    }
}

