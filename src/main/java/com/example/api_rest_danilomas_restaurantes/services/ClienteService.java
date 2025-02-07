package com.example.api_rest_danilomas_restaurantes.services;

import com.example.api_rest_danilomas_restaurantes.entities.Cliente;
import com.example.api_rest_danilomas_restaurantes.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodosLosClientes(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientesPorId(Long id){
        return clienteRepository.findById(id);
    }

    public Cliente crearCliente(Cliente cliente) throws Exception{
        if(clienteRepository.findByEmail(cliente.getEmail()).isPresent()){
            throw new Exception("El cliente ya esta en uso");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) throws Exception{
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new Exception("El cliente con ID" + id + "no existe"));

        if(!clienteExistente.getEmail().equals(cliente.getEmail())&&
                clienteRepository.findByEmail(cliente.getEmail()).isPresent()){
            throw new Exception("El cliente ya esta en uso");
        }
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setTelefono(cliente.getTelefono());
        return clienteRepository.save(clienteExistente);
    }
    public void eliminarCliente(Long id){
        clienteRepository.deleteById(id);
    }
}
