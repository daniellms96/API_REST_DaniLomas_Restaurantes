package com.example.api_rest_danilomas_restaurantes.controllers;

import com.example.api_rest_danilomas_restaurantes.DTO.ReservaDTO;
import com.example.api_rest_danilomas_restaurantes.entities.Reserva;
import com.example.api_rest_danilomas_restaurantes.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController // Marca esta clase como un controlador REST.
@RequestMapping("/api/reservas") // Define la ruta base para todos los endpoints de este controlador.
public class ReservaApiController {

    @Autowired // Inyecta el servicio de reservas.
    private ReservaService reservaService;

    // Obtiene todas las reservas disponibles en el sistema.
    @GetMapping
    public List<Reserva> obtenerReservas() {
        return reservaService.obtenerTodasLasReservas();
    }

    // Obtiene una reserva específica por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id)
                .map(reserva -> ResponseEntity.ok().body(reserva)) // Devuelve 200 (OK) si se encuentra la reserva.
                .orElse(ResponseEntity.notFound().build()); // Devuelve 404 (NOT FOUND) si no se encuentra.
    }

    // Obtiene reservas en un rango de fechas específico.
    @GetMapping("/rango")
    public List<Reserva> obtenerReservasPorRangoFechas(@RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fin) {
        return reservaService.obtenerReservasPorRangoFechas(inicio, fin);
    }

    // Crea una nueva reserva.
    @PostMapping
    public ResponseEntity<String> crearReserva(@Valid @RequestBody Reserva reserva) {
        try {
            Reserva nuevaReserva = reservaService.crearReserva(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva creada con éxito: " + nuevaReserva.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Devuelve 400 (BAD REQUEST) en caso de error.
        }
    }

    // Elimina una reserva por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 (NO CONTENT) si la eliminación fue exitosa.
    }

    // Lista reservas filtradas por una fecha específica.
    @GetMapping("/fecha")
    public ResponseEntity<List<ReservaDTO>> listarReservasPorFecha(@RequestParam("fecha") String fecha) {
        LocalDate localDate = LocalDate.parse(fecha); // Convierte el String en LocalDate.
        List<ReservaDTO> reservas = reservaService.listarReservasPorFecha(localDate);
        return ResponseEntity.ok(reservas); // Devuelve 200 (OK) con la lista de reservas encontradas.
    }

}

