package com.mpl.backend.service;

import com.mpl.backend.dto.EspecialidadDTO;
import com.mpl.backend.exception.EspecialidadNotFoundException;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public Especialidad save(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public Especialidad getById(Long id) {
        return especialidadRepository.findById(id)
            .orElseThrow(() -> new EspecialidadNotFoundException("Especialidad con ID " + id + " no encontrada"));
    }

    public void deleteById(Long id) {
        Especialidad especialidad = especialidadRepository.findById(id)
            .orElseThrow(() -> new EspecialidadNotFoundException("Especialidad con ID " + id + " no encontrada"));
        especialidadRepository.delete(especialidad);
    }

    public List<Especialidad> findAll() {
        return especialidadRepository.findAll();
    }

    public Especialidad updateEspecialidad(EspecialidadDTO especialidadDTO, Long id) {
        Especialidad especialidad = especialidadRepository.findById(id)
            .orElseThrow(() -> new EspecialidadNotFoundException("Especialidad con ID " + id + " no encontrada"));
        especialidad.setNombre(especialidadDTO.nombre());
        especialidad.setCodigo(especialidadDTO.codigo());
        return especialidadRepository.save(especialidad);
    }
}
