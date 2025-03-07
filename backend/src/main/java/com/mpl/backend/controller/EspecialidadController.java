package com.mpl.backend.controller;

import com.mpl.backend.dto.EspecialidadDTO;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.service.EspecialidadService;
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

/**
 * Controlador para la gestión de especialidades.
 * Proporciona endpoints para obtener, crear, actualizar y eliminar especialidades.
 */
@RestController
@RequestMapping("/especialidad")
@Tag(name = "Especialidades", description = "Operaciones relacionadas con la gestión de especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    /**
     * Obtiene todas las especialidades disponibles.
     * @return Lista de especialidades.
     */
    @Operation(
        summary = "Obtener todas las especialidades",
        description = "Retorna una lista de todas las especialidades disponibles.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de especialidades obtenida con éxito",
                content = @Content(
                    schema = @Schema(implementation = Especialidad.class),
                    examples = @ExampleObject(value = "[{\"id\": 1, \"nombre\": \"Cardiología\", \"descripcion\": \"Especialidad en enfermedades del corazón\"}, {\"id\": 2, \"nombre\": \"Neurología\", \"codigo\": \"AAA1\"}]")
                )
            )
        }
    )
    @GetMapping
    public List<Especialidad> getAllEspecialidades() {
        return especialidadService.findAll();
    }

/**
 * Obtiene una especialidad específica basada en su ID.
 * @param id ID de la especialidad a buscar.
 * @return Especialidad encontrada.
 */
@Operation(
    summary = "Obtener una especialidad por ID",
    description = "Retorna una especialidad específica basada en su ID.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Especialidad encontrada",
            content = @Content(
                schema = @Schema(implementation = Especialidad.class),
                examples = @ExampleObject(value = "{\"id\": 1, \"nombre\": \"Cardiología\", \"descripcion\": \"Especialidad en enfermedades del corazón\"}")
            )
        ),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    }
)
@GetMapping("/{id}")
public ResponseEntity<Especialidad> getEspecialidadById(
    @Parameter(description = "ID de la especialidad", required = true, example = "1")
    @PathVariable Long id
) {
    Especialidad especialidad = especialidadService.getById(id);
    
    // Verifica si la especialidad fue encontrada
    if (especialidad == null) {
        // Devuelve un error 404 si no se encuentra la especialidad
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    return new ResponseEntity<>(especialidad, HttpStatus.OK);
}

    /**
     * Crea una nueva especialidad con los datos proporcionados.
     * @param especialidad Datos de la especialidad a crear.
     * @return Especialidad creada.
     */
    @Operation(
        summary = "Crear una nueva especialidad",
        description = "Crea una nueva especialidad con los datos proporcionados.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la especialidad a crear",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Especialidad.class),
                examples = @ExampleObject(value = "{\"nombre\": \"Ginecología\", \"codigo\": \"AAA1\"}")
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Especialidad creada con éxito")
        }
    )
    @PostMapping("/crear")
    public Especialidad createEspecialidad(@RequestBody Especialidad especialidad) {
        return especialidadService.save(especialidad);
    }

    /**
     * Actualiza una especialidad existente basada en su ID.
     * @param especialidadDTO Datos actualizados de la especialidad.
     * @param id ID de la especialidad a actualizar.
     * @return Especialidad actualizada.
     */
    @Operation(
        summary = "Actualizar una especialidad por ID",
        description = "Actualiza los datos de una especialidad existente basada en su ID.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados de la especialidad",
            required = true,
            content = @Content(
                schema = @Schema(implementation = EspecialidadDTO.class),
                examples = @ExampleObject(value = "{\"nombre\": \"Cardiología Avanzada\", \"codigo\": \"AAA1\"}")
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Especialidad actualizada con éxito")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> updateEspecialidad(
        @RequestBody EspecialidadDTO especialidadDTO,
        @Parameter(description = "ID de la especialidad a actualizar", required = true, example = "1")
        @PathVariable Long id
    ) {
        Especialidad especialidad = especialidadService.updateEspecialidad(especialidadDTO, id);
        return new ResponseEntity<>(especialidad, HttpStatus.OK);
    }

    /**
     * Elimina una especialidad específica basada en su ID.
     * @param id ID de la especialidad a eliminar.
     */
    @Operation(
        summary = "Eliminar una especialidad por ID",
        description = "Elimina una especialidad específica basada en su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Especialidad eliminada con éxito")
        }
    )
    @DeleteMapping("/{id}")
    public void deleteEspecialidad(
        @Parameter(description = "ID de la especialidad a eliminar", required = true, example = "1")
        @PathVariable Long id
    ) {
        especialidadService.deleteById(id);
    }
}
