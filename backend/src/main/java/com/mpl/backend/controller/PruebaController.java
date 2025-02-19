package com.mpl.backend.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpl.backend.dto.PruebaDTO;
import com.mpl.backend.model.Prueba;
import com.mpl.backend.service.PruebaService;

@RestController
@RequestMapping("/pruebas")
public class PruebaController {
 @Autowired
    private PruebaService pruebaService;

    // Obtener todos los pruebas
    @GetMapping("/todos")
    public List<Prueba> getAllPruebas() {
        return pruebaService.findAll();
    }

    // Obtener un prueba por ID
    @GetMapping("/{id}")
    public Prueba getPruebaById(@PathVariable Long id) {
        return pruebaService.getById(id);
    }

    // Guardar un nuevo prueba
    @PostMapping("/crear")
    public ResponseEntity<Prueba> createPrueba(@RequestBody PruebaDTO pruebaDTO) {
        Prueba prueba = pruebaService.crearPrueba(pruebaDTO);
        return new ResponseEntity<>(prueba, HttpStatus.CREATED);
    }


    // Eliminar un prueba por ID
    @DeleteMapping("/{id}")
    public void deletePrueba(@PathVariable Long id) {
        pruebaService.deleteById(id);
    }
}    


