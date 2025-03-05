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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/evaluaciones")
@Tag(name = "Evaluaciones", description = "Operaciones relacionadas con la gestión de evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    // Obtener todas las evaluaciones
    @Operation(
        summary = "Obtener todas las evaluaciones",
        description = "Retorna una lista de todas las evaluaciones disponibles."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de evaluaciones obtenida con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontraron evaluaciones")
    })
    @GetMapping
    public List<Evaluacion> getAllEvaluaciones() {
        return evaluacionService.findAll();
    }

    // Obtener una evaluacion por ID
    @Operation(
        summary = "Obtener una evaluación por ID",
        description = "Retorna una evaluación específica basada en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluación encontrada"),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    @GetMapping("/{id}")
    public Evaluacion getEvaluacionById(
        @Parameter(description = "ID de la evaluación", required = true, example = "1")
        @PathVariable Long id
    ) {
        return evaluacionService.getById(id);
    }

    // Guardar una nueva evaluacion
    @Operation(
        summary = "Crear una nueva evaluación",
        description = "Crea una nueva evaluación con los datos proporcionados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evaluación creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/crear")
    public ResponseEntity<Evaluacion> createEvaluacion(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la evaluación a crear",
            required = true,
            content = @Content(
                schema = @Schema(implementation = EvaluacionDTO.class),
                examples = @ExampleObject(
                    value = "{\"notaFinal\": 9.5, \"participanteId\": 1, \"pruebaId\": 1}"
                )
            )
        )
        @RequestBody EvaluacionDTO evaluacionDTO
    ) {
        Evaluacion evaluacion = evaluacionService.crearEvaluacion(evaluacionDTO);
        return new ResponseEntity<>(evaluacion, HttpStatus.CREATED);
    }

    // Eliminar una evaluacion por ID
    @Operation(
        summary = "Eliminar una evaluación por ID",
        description = "Elimina una evaluación específica basada en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluación eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    @DeleteMapping("/{id}")
    public void deleteEvaluacion(
        @Parameter(description = "ID de la evaluación a eliminar", required = true, example = "1")
        @PathVariable Long id
    ) {
        evaluacionService.deleteById(id);
    }

    // Actualizar una evaluacion por ID
    @Operation(
        summary = "Actualizar una evaluación por ID",
        description = "Actualiza los datos de una evaluación existente basada en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluación actualizada con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Evaluacion> updateEvaluacion(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados de la evaluación",
            required = true,
            content = @Content(
                schema = @Schema(implementation = EvaluacionDTO.class),
                examples = @ExampleObject(
                    value = "{\"notaFinal\": 9.5, \"participanteId\": 1, \"pruebaId\": 1}"
                )
            )
        )
        @RequestBody EvaluacionDTO evaluacionDTO,
        @Parameter(description = "ID de la evaluación a actualizar", required = true, example = "1")
        @PathVariable Long id
    ) {
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

    // Obtener los ganadores (top scorers)
    @Operation(
        summary = "Obtener los ganadores (top scorers)",
        description = "Retorna una lista de los participantes con las mejores notas (top scorers)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ganadores obtenida con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontraron ganadores")
    })
    @GetMapping("/ganadores")
    public List<GanadorDTO> getTopScorers() {
        return evaluacionService.getTopScorers();
    }
}