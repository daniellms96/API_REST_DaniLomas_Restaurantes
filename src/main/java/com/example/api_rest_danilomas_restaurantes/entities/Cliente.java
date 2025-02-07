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
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    private String nombre;

    @Column(unique = true)
    @Email(message = "El email debe tener un formato valido")
    private String email;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @OneToMany(
            targetEntity = Reserva.class,
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true
            //fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Reserva> reservas;
}
