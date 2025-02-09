package com.example.api_rest_danilomas_restaurantes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mesas") // Define la tabla en la base de datos
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el ID automáticamente
    private Long id;

    @Column(name = "numeroUnico", nullable = false, unique = true) // Número único obligatorio
    @NotNull(message = "El numero no puede ir vacio")
    private Integer numeroUnico;

    @Size(message = "La descripcion no puede tener mas de 255 caracteres") // Límite de caracteres
    private String descripcion;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Evita problemas de serialización en JSON
    private List<Reserva> reservas = new ArrayList<>();
}
