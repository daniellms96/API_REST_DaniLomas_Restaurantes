package com.example.api_rest_danilomas_restaurantes.repositories;

import com.example.api_rest_danilomas_restaurantes.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que esta interfaz es un componente de acceso a datos
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método para buscar un cliente por su email
    Optional<Cliente> findByEmail(String email);
}
