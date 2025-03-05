package com.mpl.backend.service;

import com.mpl.backend.dto.EvaluacionDTO;
import com.mpl.backend.dto.GanadorDTO;
import com.mpl.backend.model.Evaluacion;
import com.mpl.backend.model.Participante;
import com.mpl.backend.model.Prueba;
import com.mpl.backend.model.User;
import com.mpl.backend.repository.EvaluacionRepository;
import com.mpl.backend.repository.ParticipanteRepository;
import com.mpl.backend.repository.PruebaRepository;
import com.mpl.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private PruebaRepository pruebaRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear una nueva Evaluacion
    public Evaluacion crearEvaluacion(EvaluacionDTO evaluacionDTO) {
        Participante participante = participanteRepository.findById(evaluacionDTO.idParticipante())
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));
        
        Prueba prueba = pruebaRepository.findById(evaluacionDTO.idPrueba())
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
        
        User user = userRepository.findById(evaluacionDTO.idUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setParticipante(participante);
        evaluacion.setPrueba(prueba);
        evaluacion.setUser(user);
        evaluacion.setNotaFinal(evaluacionDTO.notaFinal());  // Mapeo de notaFinal
        evaluacion.setEstado(evaluacionDTO.estado());        // Mapeo de estado
    
        return evaluacionRepository.save(evaluacion);
    }
    

    // Obtener todas las evaluaciones
    public List<Evaluacion> findAll() {
        return evaluacionRepository.findAll();
    }

    // Obtener una evaluacion por ID
    public Evaluacion getById(Long id) {
        return evaluacionRepository.findById(id).orElse(null);
    }

    // Eliminar una evaluacion por ID
    public void deleteById(Long id) {
        evaluacionRepository.deleteById(id);
    }

    // Actualizar una evaluacion por ID
    public Evaluacion updateEvaluacion(EvaluacionDTO evaluacionDTO, Long id) {
        Evaluacion evaluacion = evaluacionRepository.findById(id).orElse(null);
        evaluacion.setNotaFinal(null);
        evaluacion.setParticipante(participanteRepository.findById(evaluacionDTO.idParticipante()).orElse(null));
        evaluacion.setPrueba(pruebaRepository.findById(evaluacionDTO.idPrueba()).orElse(null));
        evaluacion.setUser(userRepository.findById(evaluacionDTO.idUser()).orElse(null));
        evaluacion.setEstado(null);
        return evaluacionRepository.save(evaluacion);
    }

    // Obtener los ganadores
    public List<GanadorDTO> getTopScorers() {
        List<Evaluacion> evaluaciones = evaluacionRepository.findTopScorersByTest();
        return evaluaciones.stream().map(e -> new GanadorDTO(
            e.getIdEvaluacion(),
            e.getNotaFinal(),
            e.getParticipante().getNombre(),
            e.getParticipante().getApellidos(),
            e.getPrueba().getEnunciado(),
            e.getParticipante().getEspecialidad().getNombre()
        )).collect(Collectors.toList());
    }
    // Este método maneja tanto crear como actualizar
    public Evaluacion save(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }
}
