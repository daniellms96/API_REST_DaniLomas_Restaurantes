package com.example.api_rest_danilomas_restaurantes.repositories;

import com.example.api_rest_danilomas_restaurantes.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository // Marca esta interfaz como un componente de acceso a datos
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Busca reservas dentro de un rango de fechas
    List<Reserva> findByFechaHoraBetween(LocalDateTime start, LocalDateTime end);

    // Verifica si ya existe una reserva para una mesa en una fecha y hora específicas
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.mesa.id = :mesaId AND r.fechaHora = :fechaHora")
    boolean existsByMesaAndFechaHora(@Param("mesaId") Long mesaId, @Param("fechaHora") LocalDateTime fechaHora);
}
