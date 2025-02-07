package com.example.api_rest_danilomas_restaurantes.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "fecha_hora", nullable = false)
    @NotNull(message = "La fecha y hora no pueden ser nulas")
    @FutureOrPresent(message = "La fecha de la reserva no puede ser en el pasado")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    @NotNull(message = "La mesa es obligatoria")
    private Mesa mesa;

    @Column(name = "numero_personas", nullable = false)
    @Min(value = 1, message = "El número de personas debe ser al menos 1")
    private int numeroPersonas;

}
