package com.example.api_rest_danilomas_restaurantes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration // Indica que esta clase es una configuración de Spring.
public class PasswordEncoderConfig {

    @Bean // Define un bean administrado por Spring para la codificación de contraseñas.
    public PasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder(); // Retorna una instancia de BCryptPasswordEncoder para encriptar contraseñas.
    }
}
