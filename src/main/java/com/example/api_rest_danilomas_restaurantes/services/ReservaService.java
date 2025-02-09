package com.example.api_rest_danilomas_restaurantes.services;

import com.example.api_rest_danilomas_restaurantes.DTO.ReservaDTO;
import com.example.api_rest_danilomas_restaurantes.entities.Reserva;
import com.example.api_rest_danilomas_restaurantes.repositories.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Indica que esta clase es un servicio de Spring
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // Obtiene todas las reservas
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    // Obtiene una reserva por su ID
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    // Obtiene reservas en un rango de fechas
    public List<Reserva> obtenerReservasPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return reservaRepository.findByFechaHoraBetween(inicio, fin);
    }

    // Crea una nueva reserva si la mesa está disponible
    public Reserva crearReserva(Reserva reserva) throws Exception {
        boolean mesaDisponible = verificarDisponibilidad(reserva.getMesa().getId(), reserva.getFechaHora());
        if (!mesaDisponible) {
            throw new Exception("La mesa ya está reservada para la fecha y hora especificadas.");
        }
        return reservaRepository.save(reserva);
    }

    // Elimina una reserva por su ID
    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró la reserva con ID: " + id);
        }
        reservaRepository.deleteById(id);
    }

    // Lista reservas de un día específico y las convierte en DTOs
    public List<ReservaDTO> listarReservasPorFecha(LocalDate fecha) {
        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = fecha.atTime(LocalTime.MAX);

        List<Reserva> reservas = reservaRepository.findByFechaHoraBetween(inicioDia, finDia);

        return reservas.stream()
                .map(reserva -> new ReservaDTO(
                        reserva.getCliente().getNombre(),
                        reserva.getCliente().getEmail(),
                        reserva.getFechaHora(),
                        reserva.getMesa().getNumeroUnico(),
                        reserva.getMesa().getDescripcion(),
                        reserva.getNumeroPersonas()
                ))
                .collect(Collectors.toList());
    }

    // Verifica si una mesa está disponible en una fecha y hora específicas
    private boolean verificarDisponibilidad(Long mesaId, LocalDateTime fechaHora) {
        return !reservaRepository.existsByMesaAndFechaHora(mesaId, fechaHora);
    }
}
