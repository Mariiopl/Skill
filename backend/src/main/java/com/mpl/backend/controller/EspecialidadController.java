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

@RestController
@RequestMapping("/especialidad")
@Tag(name = "Especialidades", description = "Operaciones relacionadas con la gestión de especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    // Obtener todas las especialidades
    @Operation(
        summary = "Obtener todas las especialidades",
        description = "Retorna una lista de todas las especialidades disponibles."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de especialidades obtenida con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontraron especialidades")
    })
    @GetMapping
    public List<Especialidad> getAllEspecialidades() {
        return especialidadService.findAll();
    }

    // Obtener una especialidad por ID
    @Operation(
        summary = "Obtener una especialidad por ID",
        description = "Retorna una especialidad específica basada en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad encontrada"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    @GetMapping("/{id}")
    public Especialidad getEspecialidadById(
        @Parameter(description = "ID de la especialidad", required = true, example = "1")
        @PathVariable Long id
    ) {
        return especialidadService.getById(id);
    }

    // Guardar una nueva especialidad
    @Operation(
        summary = "Crear una nueva especialidad",
        description = "Crea una nueva especialidad con los datos proporcionados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/crear")
    public Especialidad createEspecialidad(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la especialidad a crear",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Especialidad.class),
                examples = @ExampleObject(
                    value = "{\"nombre\": \"Cardiología\", \"descripcion\": \"Especialidad en enfermedades del corazón\"}"
                )
            )
        )
        @RequestBody Especialidad especialidad
    ) {
        return especialidadService.save(especialidad);
    }

    // Eliminar una especialidad por ID
    @Operation(
        summary = "Eliminar una especialidad por ID",
        description = "Elimina una especialidad específica basada en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    @DeleteMapping("/{id}")
    public void deleteEspecialidad(
        @Parameter(description = "ID de la especialidad a eliminar", required = true, example = "1")
        @PathVariable Long id
    ) {
        especialidadService.deleteById(id);
    }

    // Actualizar una especialidad por ID
    @Operation(
        summary = "Actualizar una especialidad por ID",
        description = "Actualiza los datos de una especialidad existente basada en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad actualizada con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> updateEspecialidad(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados de la especialidad",
            required = true,
            content = @Content(
                schema = @Schema(implementation = EspecialidadDTO.class),
                examples = @ExampleObject(
                    value = "{\"nombre\": \"Cardiología\", \"descripcion\": \"Especialidad en enfermedades del corazón\"}"
                )
            )
        )
        @RequestBody EspecialidadDTO especialidadDTO,
        @Parameter(description = "ID de la especialidad a actualizar", required = true, example = "1")
        @PathVariable Long id
    ) {
        Especialidad especialidad = especialidadService.updateEspecialidad(especialidadDTO, id);
        return new ResponseEntity<>(especialidad, HttpStatus.OK);
    }
}