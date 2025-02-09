package com.example.api_rest_danilomas_restaurantes.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data  // Genera automáticamente los getters, setters, `toString()`, `equals()` y `hashCode()`.
@Builder  // Permite construir objetos con el patrón Builder.
@AllArgsConstructor  // Genera un constructor con todos los argumentos.
public class ReservaDTO {
    private String nombreCliente;   // Nombre del cliente que realiza la reserva.
    private String emailCliente;    // Correo electrónico del cliente.
    private LocalDateTime fechaReserva;  // Fecha y hora de la reserva.
    private Integer numeroMesa;     // Número de la mesa asignada.
    private String descripcionMesa; // Descripción de la mesa (ej. ubicación o tipo).
    private Integer numeroPersonas; // Cantidad de personas en la reserva.
}

