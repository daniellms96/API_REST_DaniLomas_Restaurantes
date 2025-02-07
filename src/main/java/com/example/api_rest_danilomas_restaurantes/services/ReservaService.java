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

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public List<Reserva> obtenerReservasPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return reservaRepository.findByFechaHoraBetween(inicio, fin);
    }

    public Reserva crearReserva(Reserva reserva) throws Exception {
        boolean mesaDisponible = verificarDisponibilidad(reserva.getMesa().getId(), reserva.getFechaHora());
        if (!mesaDisponible) {
            throw new Exception("La mesa ya está reservada para la fecha y hora especificadas.");
        }
        return reservaRepository.save(reserva);
    }

    //    public Reserva actualizarReserva(Long id, Reserva reserva) throws Exception {
//        Reserva reservaExistente = reservaRepository.findById(id)
//                .orElseThrow(() -> new Exception("Reserva con ID " + id + " no existe"));
//        reservaExistente.setFechaHora(reserva.getFechaHora());
//        reservaExistente.setCliente(reserva.getCliente());
//        reservaExistente.setMesa(reserva.getMesa());
//        reservaExistente.setNumeroPersonas(reserva.getNumeroPersonas());
//
//        return reservaRepository.save(reservaExistente);
//    }
    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró la reserva con ID: " + id);
        }
        reservaRepository.deleteById(id);
    }
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

    private boolean verificarDisponibilidad(Long mesaId, LocalDateTime fechaHora) {
        return !reservaRepository.existsByMesaAndFechaHora(mesaId, fechaHora);
    }

}
