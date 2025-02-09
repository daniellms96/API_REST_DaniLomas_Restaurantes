package com.example.api_rest_danilomas_restaurantes.repositories;

import com.example.api_rest_danilomas_restaurantes.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que esta interfaz es un componente de acceso a datos
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    // Método para buscar una mesa por su número único
    Optional<Mesa> findByNumeroUnico(Integer numeroUnico);
}
