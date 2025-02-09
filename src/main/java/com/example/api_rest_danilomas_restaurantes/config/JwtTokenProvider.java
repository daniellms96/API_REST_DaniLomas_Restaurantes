package com.example.api_rest_danilomas_restaurantes.config;


import com.example.api_rest_danilomas_restaurantes.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component // Marca esta clase como un componente de Spring para que pueda ser detectada automáticamente.
public class JwtTokenProvider {

    // Clave secreta utilizada para firmar y verificar los tokens JWT.
    private static final String SECRET_KEY = "zskfldj394852l3kj4tho9a8yt9qa4)()(%&asfdasdrtg45545·%·%";

    // Tiempo de expiración del token en milisegundos (1 día).
    private static final long EXPIRATION_TIME = 86400000;

    // Método para generar un token JWT basado en la autenticación del usuario.
    public String generateToken(Authentication authentication) {
        // Obtiene la entidad del usuario autenticado desde el contexto de seguridad.
        UserEntity user = (UserEntity) authentication.getPrincipal();

        // Genera una clave secreta a partir de la clave definida.
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        // Construye y devuelve el token JWT.
        return Jwts.builder()
                .subject(Long.toString(user.getId())) // Asigna el ID del usuario como sujeto del token.
                .claim("email", user.getEmail()) // Agrega el email del usuario como un claim.
                .claim("username", user.getUsername()) // Agrega el nombre de usuario como un claim.
                .claim("foto", "default.jpg") // Agrega un claim fijo para la foto de perfil.
                .issuedAt(new Date()) // Establece la fecha de emisión del token.
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Define la fecha de expiración del token.
                .signWith(key) // Firma el token con la clave secreta.
                .compact(); // Genera el token en formato compacto.
    }

    // Método para validar la firma y la estructura del token.
    public boolean isValidToken(String token) {
        // Si el token está vacío o en blanco, se considera inválido.
        if (StringUtils.isBlank(token)) {
            return false;
        }

        // Genera una clave secreta basada en la clave definida.
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        try {
            // Crea un validador de tokens JWT.
            JwtParser validator = Jwts.parser()
                    .verifyWith(key) // Usa la clave secreta para verificar la firma del token.
                    .build();

            // Intenta analizar el token. Si es válido, no lanza excepciones.
            validator.parse(token);
            return true;
        } catch (Exception e) {
            // Si hay un error en la validación (firma incorrecta o token expirado), se captura la excepción.
            // Aquí podríamos registrar el error en los logs.
            System.err.println("Error al validar el token: " + e.getMessage());
            return false;
        }
    }

    // Método para extraer el nombre de usuario desde un token JWT.
    public String getUsernameFromToken(String token) {
        // Crea un parser para el token usando la clave secreta.
        JwtParser parser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build();

        // Extrae los claims (información contenida en el token).
        Claims claims = parser.parseClaimsJws(token).getBody();

        // Retorna el valor del claim "username".
        return claims.get("username").toString();
    }
}

