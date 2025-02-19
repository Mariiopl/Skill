package com.mpl.backend.controller;


import com.mpl.backend.model.Especialidad;
import com.mpl.backend.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidad")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    // Obtener todos los participantes
    @GetMapping
    public List<Especialidad> getAllEspecialidades() {
        return especialidadService.findAll();
    }

    // Obtener un participante por ID
    @GetMapping("/{id}")
    public Especialidad getEspecialidadById(@PathVariable Long id) {
        return especialidadService.getById(id);
    }

    // Guardar un nuevo participante
    @PostMapping
    public Especialidad createEspecialidad(@RequestBody Especialidad especialidad) {
        return especialidadService.save(especialidad);
    }

    // Eliminar un participante por ID
    @DeleteMapping("/{id}")
    public void deleteEspecialidad(@PathVariable Long id) {
        especialidadService.deleteById(id);
    }
}
