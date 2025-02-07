package com.example.api_rest_danilomas_restaurantes.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ReservaDTO {
    private String nombreCliente;
    private String emailCliente;
    private LocalDateTime fechaReserva;
    private Integer numeroMesa;
    private String descripcionMesa;
    private Integer numeroPersonas;

}
