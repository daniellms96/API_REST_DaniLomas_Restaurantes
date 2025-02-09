package com.example.api_rest_danilomas_restaurantes.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Genera automáticamente los getters, setters, `toString()`, `equals()` y `hashCode()`.
@NoArgsConstructor  // Genera un constructor sin argumentos.
@AllArgsConstructor  // Genera un constructor con todos los argumentos.
@Builder  // Permite construir objetos de esta clase usando el patrón Builder.
public class LoginRequestDTO {
    private String username; // Nombre de usuario que enviará el cliente para autenticarse.
    private String password; // Contraseña del usuario.
}

