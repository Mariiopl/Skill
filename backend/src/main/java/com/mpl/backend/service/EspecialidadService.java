package com.mpl.backend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpl.backend.dto.EspecialidadDTO;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.repository.EspecialidadRepository;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public Especialidad save(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public Especialidad getById(Long id) {
        return especialidadRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        especialidadRepository.deleteById(id);
    }

    public List<Especialidad> findAll() {
        return especialidadRepository.findAll();
    }

    public Especialidad updateEspecialidad(EspecialidadDTO especialidadDTO, Long id) {
        Especialidad especialidad = especialidadRepository.findById(id).orElse(null);
        especialidad.setNombre(especialidadDTO.nombre());
        especialidad.setCodigo(especialidadDTO.codigo());
        return especialidadRepository.save(especialidad);
    }

}


