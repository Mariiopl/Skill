package com.mpl.backend.controller;

import com.mpl.backend.dto.EvaluacionDTO;
import com.mpl.backend.dto.GanadorDTO;
import com.mpl.backend.model.Evaluacion;
import com.mpl.backend.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    // Obtener todas las evaluaciones
    @GetMapping
    public List<Evaluacion> getAllEvaluaciones() {
        return evaluacionService.findAll();
    }

    // Obtener una evaluacion por ID
    @GetMapping("/{id}")
    public Evaluacion getEvaluacionById(@PathVariable Long id) {
        return evaluacionService.getById(id);
    }

    // Guardar una nueva evaluacion
    @PostMapping("/crear")
    public ResponseEntity<Evaluacion> createEvaluacion(@RequestBody EvaluacionDTO evaluacionDTO) {
        Evaluacion evaluacion = evaluacionService.crearEvaluacion(evaluacionDTO);
        return new ResponseEntity<>(evaluacion, HttpStatus.CREATED);
    }

    // Eliminar una evaluacion por ID
    @DeleteMapping("/{id}")
    public void deleteEvaluacion(@PathVariable Long id) {
        evaluacionService.deleteById(id);
    }
    
    // Actualizar una evaluacion por ID
@PutMapping("/{id}")
public ResponseEntity<Evaluacion> updateEvaluacion(@RequestBody EvaluacionDTO evaluacionDTO, @PathVariable Long id) {
    // Obtener la evaluación existente
    Evaluacion evaluacion = evaluacionService.getById(id);
    
    // Actualizar los campos, asegúrate de que notaFinal no sea null
    if (evaluacionDTO.notaFinal() != null) {
        evaluacion.setNotaFinal(evaluacionDTO.notaFinal());
    }
    
    // Actualizar otros campos si es necesario
    evaluacionService.save(evaluacion);
    
    return new ResponseEntity<>(evaluacion, HttpStatus.OK);
}

    @GetMapping("/ganadores")
    public List<GanadorDTO> getTopScorers() {
        return evaluacionService.getTopScorers();
    }
}
