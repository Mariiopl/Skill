package com.mpl.backend.controller;


import com.mpl.backend.dto.EspecialidadDTO;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidad")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    // Obtener todas las especialidades
    @GetMapping
    public List<Especialidad> getAllEspecialidades() {
        return especialidadService.findAll();
    }

    // Obtener una especialidad por ID
    @GetMapping("/{id}")
    public Especialidad getEspecialidadById(@PathVariable Long id) {
        return especialidadService.getById(id);
    }

    // Guardar una nueva especialidad
    @PostMapping("/crear")
    public Especialidad createEspecialidad(@RequestBody Especialidad especialidad) {
        return especialidadService.save(especialidad);
    }

    // Eliminar una especialidad por ID
    @DeleteMapping("/{id}")
    public void deleteEspecialidad(@PathVariable Long id) {
        especialidadService.deleteById(id);
    }
    // Actualizar una especialidad por ID
    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> updateEspecialidad(@RequestBody EspecialidadDTO especialidadDTO, @PathVariable Long id) {
        Especialidad especialidad = especialidadService.updateEspecialidad(especialidadDTO, id);
        return new ResponseEntity<>(especialidad, HttpStatus.OK);
    }
}
