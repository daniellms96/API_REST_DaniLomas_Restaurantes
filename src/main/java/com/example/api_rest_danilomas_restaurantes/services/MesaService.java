package com.example.api_rest_danilomas_restaurantes.services;

import com.example.api_rest_danilomas_restaurantes.entities.Mesa;
import com.example.api_rest_danilomas_restaurantes.repositories.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta clase es un servicio de Spring
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    // Obtiene todas las mesas
    public List<Mesa> obtenerTodasLasMesas() {
        return mesaRepository.findAll();
    }

    // Obtiene una mesa por su ID
    public Optional<Mesa> obtenerMesaPorId(Long id) {
        return mesaRepository.findById(id);
    }

    // Crea una nueva mesa si el número único no está en uso
    public Mesa crearMesa(Mesa mesa) throws Exception {
        if (mesaRepository.findByNumeroUnico(mesa.getNumeroUnico()).isPresent()) {
            throw new Exception("El número único (mesa) ya está en uso");
        }
        return mesaRepository.save(mesa);
    }

    // Actualiza una mesa existente
    public Mesa actualizarMesa(Long id, Mesa mesa) throws Exception {
        Mesa mesaExistente = mesaRepository.findById(id)
                .orElseThrow(() -> new Exception("La mesa con ID " + id + " no existe"));

        // Verifica si el número único ya está en uso por otra mesa
        if (!mesaExistente.getNumeroUnico().equals(mesa.getNumeroUnico()) &&
                mesaRepository.findByNumeroUnico(mesa.getNumeroUnico()).isPresent()) {
            throw new Exception("El número único (mesa) ya está en uso");
        }

        // Actualiza los datos de la mesa
        mesaExistente.setNumeroUnico(mesa.getNumeroUnico());
        mesaExistente.setDescripcion(mesa.getDescripcion());

        return mesaRepository.save(mesaExistente);
    }

    // Elimina una mesa por su ID
    public void eliminarMesa(Long id) {
        mesaRepository.deleteById(id);
    }
}
