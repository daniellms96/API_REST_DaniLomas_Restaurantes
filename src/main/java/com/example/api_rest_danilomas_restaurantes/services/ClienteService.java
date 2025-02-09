package com.example.api_rest_danilomas_restaurantes.services;

import com.example.api_rest_danilomas_restaurantes.entities.Cliente;
import com.example.api_rest_danilomas_restaurantes.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta clase es un servicio de Spring
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtiene todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    // Obtiene un cliente por su ID
    public Optional<Cliente> obtenerClientesPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Crea un nuevo cliente si el email no está en uso
    public Cliente crearCliente(Cliente cliente) throws Exception {
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new Exception("El cliente ya está en uso");
        }
        return clienteRepository.save(cliente);
    }

    // Actualiza un cliente existente
    public Cliente actualizarCliente(Long id, Cliente cliente) throws Exception {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new Exception("El cliente con ID " + id + " no existe"));

        // Verifica si el email ya está en uso por otro cliente
        if (!clienteExistente.getEmail().equals(cliente.getEmail()) &&
                clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new Exception("El cliente ya está en uso");
        }

        // Actualiza los datos del cliente
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setTelefono(cliente.getTelefono());

        return clienteRepository.save(clienteExistente);
    }

    // Elimina un cliente por su ID
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
