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

@RestController
@RequestMapping("/api/reservas")
public class ReservaApiController {
    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> obtenerReservas(){
        return reservaService.obtenerTodasLasReservas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id){
        return reservaService.obtenerReservaPorId(id)
                .map(reserva -> ResponseEntity.ok().body(reserva))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rango")
    public List<Reserva> obtenerReservasPorRangoFechas(@RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fin){
        return reservaService.obtenerReservasPorRangoFechas(inicio, fin);
    }

    @PostMapping
    public ResponseEntity<String> crearReserva(@Valid @RequestBody Reserva reserva){
        try{
            Reserva nuevaReserva = reservaService.crearReserva(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva creada con exito: "+nuevaReserva.getId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Reserva>actualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva){
//        try{
//            return ResponseEntity.ok(reservaService.actualizarReserva(id, reserva));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id){
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<ReservaDTO>> listarReservasPorFecha(@RequestParam("fecha") String fecha) {
        LocalDate localDate = LocalDate.parse(fecha); // Convierte el String en LocalDate
        List<ReservaDTO> reservas = reservaService.listarReservasPorFecha(localDate);
        return ResponseEntity.ok(reservas);
    }

}
