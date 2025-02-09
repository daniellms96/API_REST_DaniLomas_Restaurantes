package com.example.api_rest_danilomas_restaurantes.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) utilizado para la solicitud de registro de usuario.
 * Contiene los datos básicos necesarios para registrar un usuario en el sistema.
 */
@Data  // Genera automáticamente los métodos getter, setter, toString(), equals() y hashCode()
@AllArgsConstructor  // Genera un constructor con todos los argumentos
@NoArgsConstructor   // Genera un constructor vacío (sin argumentos)
@Builder  // Permite construir objetos de esta clase usando el patrón Builder
public class UserRegisterDTO {
    private String username;  // Nombre de usuario que el cliente desea registrar
    private String email;  // Dirección de correo electrónico del usuario
    private String password;  // Contraseña ingresada por el usuario
    private String password2;  // Confirmación de la contraseña
}