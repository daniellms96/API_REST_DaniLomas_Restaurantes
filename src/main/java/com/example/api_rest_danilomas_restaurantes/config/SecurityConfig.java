package com.example.api_rest_danilomas_restaurantes.config;

import com.example.api_rest_danilomas_restaurantes.repositories.UserEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indica que esta clase es una configuración de Spring.
public class SecurityConfig {

    private JwtFilter jwtFilter; // Filtro JWT para interceptar y validar los tokens en las solicitudes.
    private final UserDetailsService userDetailsService; // Servicio para obtener los detalles de usuario.

    // Constructor que inyecta el servicio de detalles de usuario y el filtro JWT.
    public SecurityConfig(UserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean // Define un bean para el gestor de autenticación.
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // Proveedor de autenticación basado en DAO.
        authProvider.setUserDetailsService(userDetailsService); // Configura el servicio de detalles de usuario.
        authProvider.setPasswordEncoder(passwordEncoder); // Configura el codificador de contraseñas.
        return new ProviderManager(authProvider); // Devuelve un gestor de autenticación con el proveedor configurado.
    }

    @Bean // Define un bean para la cadena de filtros de seguridad de Spring Security.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())   // Se deshabilita CSRF ya que las API REST usan tokens en lugar de sesiones.
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura una política de sesión sin estado, ya que usaremos tokens JWT.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Permite el acceso sin autenticación a la documentación de Swagger.
                        .requestMatchers("/auth/**").permitAll() // Permite acceso sin autenticación a los endpoints de autenticación.
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud.
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Añade el filtro JWT antes del filtro de autenticación por usuario y contraseña.

        return http.build(); // Construye y devuelve la configuración de seguridad.
    }
}

