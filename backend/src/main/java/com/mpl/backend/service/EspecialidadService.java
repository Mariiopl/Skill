package com.mpl.backend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpl.backend.model.Especialidad;
import com.mpl.backend.repository.EspecialidadRepository;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public Especialidad save(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public Especialidad getById(Integer id) {
        return especialidadRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        especialidadRepository.deleteById(id);
    }

    public List<Especialidad> findAll() {
        return especialidadRepository.findAll();
    }
}


