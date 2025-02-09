package com.example.api_rest_danilomas_restaurantes.repositories;

import com.example.api_rest_danilomas_restaurantes.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que esta interfaz es un componente de repositorio
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    // Busca un usuario por su nombre de usuario
    Optional<UserEntity> findByUsername(String username);

    // Busca un usuario por su email
    Optional<UserEntity> findByEmail(String email);
}
