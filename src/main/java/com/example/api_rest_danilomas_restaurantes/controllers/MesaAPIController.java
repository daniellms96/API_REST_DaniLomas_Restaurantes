package com.example.api_rest_danilomas_restaurantes.controllers;

import com.example.api_rest_danilomas_restaurantes.entities.Mesa;
import com.example.api_rest_danilomas_restaurantes.services.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaAPIController {
    @Autowired
    private MesaService mesaService;

    @GetMapping
    public List<Mesa> obtenerMesas(){
        return mesaService.obtenerTodasLasMesas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> obtenerMesaPorId(@PathVariable Long id){
        return mesaService.obtenerMesaPorId(id)
                .map(mesa -> ResponseEntity.ok().body(mesa))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Mesa> crearMesa(@RequestBody Mesa mesa){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(mesaService.crearMesa(mesa));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Mesa> actualizarMesa(@PathVariable Long id, @RequestBody Mesa mesa){
        try{
            return ResponseEntity.ok(mesaService.actualizarMesa(id, mesa));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable Long id){
        mesaService.eliminarMesa(id);
        return ResponseEntity.noContent().build();
    }

}
