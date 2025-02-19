package com.mpl.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpl.backend.dto.ParticipanteDTO;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.model.Participante;
import com.mpl.backend.repository.EspecialidadRepository;
import com.mpl.backend.repository.ParticipanteRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    // Aquí debes inyectar EspecialidadRepository también
    @Autowired
    private EspecialidadRepository especialidadRepository;

    public Participante crearParticipante(ParticipanteDTO participanteDTO) {
        Especialidad especialidad = especialidadRepository.findById(participanteDTO.idEspecialidad())
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
    
        Participante participante = new Participante();
        participante.setNombre(participanteDTO.nombre());
        participante.setApellidos(participanteDTO.apellidos());
        participante.setCentro(participanteDTO.centro());
        participante.setEspecialidad(especialidad);
    
        return participanteRepository.save(participante);
    }
    @PostConstruct
    public void init() {
        testCreate();
    }
    public void testCreate() {
        Especialidad especialidad = especialidadRepository.findById(1L).orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
    
        ParticipanteDTO dto = new ParticipanteDTO("Carlos", "Ramírez", "Centro Ejemplo", 1L);
        Participante participante = new Participante();
        participante.setNombre(dto.nombre());
        participante.setApellidos(dto.apellidos());
        participante.setCentro(dto.centro());
        participante.setEspecialidad(especialidad);
    
        participanteRepository.save(participante);
    }
        
    

    public Participante save(Participante participante) {
        return participanteRepository.save(participante);
    }

    public Participante getById(Long id) {
        return participanteRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        participanteRepository.deleteById(id);
    }

    public List<Participante> findAll() {
        return participanteRepository.findAll();
    }
}
