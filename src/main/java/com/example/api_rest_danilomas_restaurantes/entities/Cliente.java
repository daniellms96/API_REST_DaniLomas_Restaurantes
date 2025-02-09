package com.example.api_rest_danilomas_restaurantes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes") // Define la tabla en la base de datos
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el ID automáticamente
    private Long id;

    @NotBlank
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    private String nombre;

    @Column(unique = true) // Evita duplicados en el email
    @Email(message = "El email debe tener un formato valido")
    private String email;

    @Column(length = 15) // Define la longitud máxima del teléfono
    private String telefono;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Evita la recursión infinita en las respuestas JSON
    private List<Reserva> reservas;
}
