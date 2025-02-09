package com.example.api_rest_danilomas_restaurantes.controllers;

import com.example.api_rest_danilomas_restaurantes.entities.Cliente;
import com.example.api_rest_danilomas_restaurantes.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase es un controlador REST en Spring.
@RequestMapping("/api/clientes") // Define la ruta base para todos los endpoints de este controlador.
public class ClienteAPIController {

    @Autowired // Inyecta el servicio ClienteService para gestionar la lógica de negocio.
    private ClienteService clienteService;

    @GetMapping // Endpoint para obtener la lista de todos los clientes.
    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerTodosLosClientes(); // Llama al servicio para obtener todos los clientes.
    }

    @GetMapping("/{id}") // Endpoint para obtener un cliente específico por su ID.
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        return clienteService.obtenerClientesPorId(id) // Llama al servicio para buscar el cliente.
                .map(cliente -> ResponseEntity.ok().body(cliente)) // Si se encuentra, devuelve 200 (OK) con el cliente.
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra, devuelve 404 (NOT FOUND).
    }

    @PostMapping // Endpoint para crear un nuevo cliente.
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearCliente(cliente)); // Devuelve 201 (CREATED) con el cliente creado.
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Si hay error, devuelve 400 (BAD REQUEST).
        }
    }

    @PutMapping("/{id}") // Endpoint para actualizar un cliente existente.
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(clienteService.actualizarCliente(id, cliente)); // Si se actualiza, devuelve 200 (OK) con el cliente actualizado.
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Si hay error, devuelve 400 (BAD REQUEST).
        }
    }

    @DeleteMapping("/{id}") // Endpoint para eliminar un cliente por su ID.
    public ResponseEntity<Cliente> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id); // Llama al servicio para eliminar el cliente.
        return ResponseEntity.noContent().build(); // Devuelve 204 (NO CONTENT) indicando que la eliminación fue exitosa.
    }
}

