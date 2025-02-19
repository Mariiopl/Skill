package com.mpl.backend.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mpl.backend.dto.PruebaDTO;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.model.Prueba;
import com.mpl.backend.repository.EspecialidadRepository;
import com.mpl.backend.repository.PruebaRepository;

@Service
public class PruebaService {

    @Autowired
    private PruebaRepository pruebaRepository;
    
    // Aquí debes inyectar EspecialidadRepository también
    @Autowired
    private EspecialidadRepository especialidadRepository;


    public Prueba crearPrueba(PruebaDTO pruebaDTO) {
        Especialidad especialidad = especialidadRepository.findById(pruebaDTO.idEspecialidad())
        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        Prueba prueba = new Prueba();
        prueba.setEnunciado(pruebaDTO.enunciado());
        prueba.setPuntuacionMaxima(pruebaDTO.puntuacionMaxima());
        prueba.setEspecialidad(especialidad);
        return pruebaRepository.save(prueba);
    }  

    public Prueba save(Prueba prueba) {
        return pruebaRepository.save(prueba);
    }

    public Prueba getById(Long id) {
        return pruebaRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        pruebaRepository.deleteById(id);
    }

    public List<Prueba> findAll() {
        return pruebaRepository.findAll();
    }

}

