package com.example.api_rest_danilomas_restaurantes.repositories;

import com.example.api_rest_danilomas_restaurantes.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Optional<Mesa> findByNumeroUnico(Integer numeroUnico);
}
