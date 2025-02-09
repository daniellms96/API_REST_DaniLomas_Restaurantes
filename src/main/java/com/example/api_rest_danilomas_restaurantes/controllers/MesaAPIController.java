package com.example.api_rest_danilomas_restaurantes.controllers;

import com.example.api_rest_danilomas_restaurantes.entities.Mesa;
import com.example.api_rest_danilomas_restaurantes.services.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marca esta clase como un controlador REST de Spring.
@RequestMapping("/api/mesas") // Define la ruta base para los endpoints de esta API.
public class MesaAPIController {

    @Autowired // Inyecta el servicio MesaService para manejar la lógica de negocio de las mesas.
    private MesaService mesaService;

    @GetMapping // Endpoint para obtener la lista de todas las mesas.
    public List<Mesa> obtenerMesas() {
        return mesaService.obtenerTodasLasMesas(); // Llama al servicio para obtener todas las mesas.
    }

    @GetMapping("/{id}") // Endpoint para obtener una mesa específica por su ID.
    public ResponseEntity<Mesa> obtenerMesaPorId(@PathVariable Long id) {
        return mesaService.obtenerMesaPorId(id) // Llama al servicio para buscar la mesa por ID.
                .map(mesa -> ResponseEntity.ok().body(mesa)) // Si la encuentra, devuelve 200 (OK) con la mesa.
                .orElse(ResponseEntity.notFound().build()); // Si no la encuentra, devuelve 404 (NOT FOUND).
    }

    @PostMapping // Endpoint para crear una nueva mesa.
    public ResponseEntity<Mesa> crearMesa(@RequestBody Mesa mesa) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(mesaService.crearMesa(mesa)); // Devuelve 201 (CREATED) con la mesa creada.
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Si hay error, devuelve 400 (BAD REQUEST).
        }
    }

    @PutMapping("/{id}") // Endpoint para actualizar una mesa existente.
    public ResponseEntity<Mesa> actualizarMesa(@PathVariable Long id, @RequestBody Mesa mesa) {
        try {
            return ResponseEntity.ok(mesaService.actualizarMesa(id, mesa)); // Si la actualización es exitosa, devuelve 200 (OK).
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Si hay error, devuelve 400 (BAD REQUEST).
        }
    }

    @DeleteMapping("/{id}") // Endpoint para eliminar una mesa por su ID.
    public ResponseEntity<Void> eliminarMesa(@PathVariable Long id) {
        mesaService.eliminarMesa(id); // Llama al servicio para eliminar la mesa.
        return ResponseEntity.noContent().build(); // Devuelve 204 (NO CONTENT) indicando que la eliminación fue exitosa.
    }
}

