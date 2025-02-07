package com.example.api_rest_danilomas_restaurantes.services;

import com.example.api_rest_danilomas_restaurantes.entities.Mesa;
import com.example.api_rest_danilomas_restaurantes.repositories.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    public List<Mesa>obtenerTodasLasMesas(){
        return mesaRepository.findAll();
    }

    public Optional<Mesa> obtenerMesaPorId(Long id){
        return mesaRepository.findById(id);
    }

    public Mesa crearMesa(Mesa mesa) throws Exception{
        if(mesaRepository.findByNumeroUnico(mesa.getNumeroUnico()).isPresent()){
            throw new Exception("El numero unico (mesa) ya esta en uso");
        }
        return mesaRepository.save(mesa);
    }
    public Mesa actualizarMesa(Long id, Mesa mesa) throws Exception{
        Mesa mesaEXistente = mesaRepository.findById(id)
                .orElseThrow(() -> new Exception("La mesa con ID " + id + " no existe"));

        if(!mesaEXistente.getNumeroUnico().equals(mesa.getNumeroUnico()) &&
                mesaRepository.findByNumeroUnico(mesa.getNumeroUnico()).isPresent()){
            throw new Exception("El numero unico (mesa) ya esta en uso");
        }
        mesaEXistente.setNumeroUnico(mesa.getNumeroUnico());
        mesaEXistente.setDescripcion(mesa.getDescripcion());
        return mesaRepository.save(mesaEXistente);
    }
    public void eliminarMesa(Long id){
        mesaRepository.deleteById(id);
    }

}
