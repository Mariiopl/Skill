package com.mpl.backend.controller;

import com.mpl.backend.dto.ParticipanteDTO;
import com.mpl.backend.model.Participante;
import com.mpl.backend.service.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    // Obtener todos los participantes
    @GetMapping("/todos")
    public List<Participante> getAllParticipantes() {
        return participanteService.findAll();
    }

    // Obtener un participante por ID
    @GetMapping("/{id}")
    public Participante getParticipanteById(@PathVariable Long id) {
        return participanteService.getById(id);
    }

    // Guardar un nuevo participante
    @PostMapping("/crear")
    public ResponseEntity<Participante> createParticipante(@RequestBody ParticipanteDTO participanteDTO) {
        Participante participante = participanteService.crearParticipante(participanteDTO);
        return new ResponseEntity<>(participante, HttpStatus.CREATED);
    }


    // Eliminar un participante por ID
    @DeleteMapping("/{id}")
    public void deleteParticipante(@PathVariable Long id) {
        participanteService.deleteById(id);
    }

    // Actualizar un participante por ID
    @PutMapping("/{id}")
    public ResponseEntity<Participante> updateParticipante(@RequestBody ParticipanteDTO participanteDTO, @PathVariable Long id) {
        Participante participante = participanteService.updateParticipante(participanteDTO, id);
        return new ResponseEntity<>(participante, HttpStatus.OK);
    }
}
