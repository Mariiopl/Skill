package com.mpl.backend.controller;

import com.mpl.backend.model.Participante;
import com.mpl.backend.service.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    // Obtener todos los participantes
    @GetMapping
    public List<Participante> getAllParticipantes() {
        return participanteService.findAll();
    }

    // Obtener un participante por ID
    @GetMapping("/{id}")
    public Participante getParticipanteById(@PathVariable Integer id) {
        return participanteService.getById(id);
    }

    // Guardar un nuevo participante
    @PostMapping
    public Participante createParticipante(@RequestBody Participante participante) {
        return participanteService.save(participante);
    }

    // Eliminar un participante por ID
    @DeleteMapping("/{id}")
    public void deleteParticipante(@PathVariable Integer id) {
        participanteService.deleteById(id);
    }
}
