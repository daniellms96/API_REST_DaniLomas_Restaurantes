package com.example.api_rest_danilomas_restaurantes.controllers;

import com.example.api_rest_danilomas_restaurantes.DTO.LoginRequestDTO;
import com.example.api_rest_danilomas_restaurantes.DTO.LoginResponseDTO;
import com.example.api_rest_danilomas_restaurantes.DTO.UserRegisterDTO;
import com.example.api_rest_danilomas_restaurantes.config.JwtTokenProvider;
import com.example.api_rest_danilomas_restaurantes.entities.UserEntity;
import com.example.api_rest_danilomas_restaurantes.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController // Indica que esta clase es un controlador REST en Spring.
public class AuthController {

    @Autowired // Inyecta el repositorio de usuarios para interactuar con la base de datos.
    private UserEntityRepository userRepository;

    @Autowired // Inyecta el codificador de contraseñas para encriptar y verificar contraseñas.
    private PasswordEncoder passwordEncoder;

    @Autowired // Inyecta el proveedor de tokens JWT para generar y validar tokens.
    private JwtTokenProvider tokenProvider;

    @Autowired // Inyecta el administrador de autenticación de Spring Security.
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/register") // Endpoint para registrar un nuevo usuario.
    public ResponseEntity<UserEntity> save(@RequestBody UserRegisterDTO userDTO) {
        // Crea una nueva entidad de usuario con los datos proporcionados en la solicitud.
        UserEntity userEntity = this.userRepository.save(
                UserEntity.builder()
                        .username(userDTO.getUsername()) // Asigna el nombre de usuario.
                        .password(passwordEncoder.encode(userDTO.getPassword())) // Codifica la contraseña antes de guardarla.
                        .email(userDTO.getEmail()) // Asigna el email.
                        .authorities(List.of("ROLE_USER", "ROLE_ADMIN")) // Asigna roles por defecto.
                        .build());

        // Retorna un código 201 (CREATED) con el usuario registrado.
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
    }

    @PostMapping("/auth/login") // Endpoint para iniciar sesión.
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            // Crea un token de autenticación con las credenciales proporcionadas.
            UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());

            // Autentica al usuario en Spring Security.
            Authentication auth = authenticationManager.authenticate(userPassAuthToken);

            // Obtiene el usuario autenticado.
            UserEntity user = (UserEntity) auth.getPrincipal();

            // Genera un token JWT para el usuario autenticado.
            String token = this.tokenProvider.generateToken(auth);

            // Retorna un código 200 (OK) con el nombre de usuario y el token JWT.
            return ResponseEntity.ok(new LoginResponseDTO(user.getUsername(), token));
        } catch (Exception e) {
            // Si las credenciales son incorrectas, devuelve un código 401 (UNAUTHORIZED).
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of(
                            "path", "/auth/login",
                            "message", "Credenciales erróneas",
                            "timestamp", new Date()
                    )
            );
        }
    }
}

