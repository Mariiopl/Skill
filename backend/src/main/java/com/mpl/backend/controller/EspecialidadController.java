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

    /**
     * Endpoint para obtener todas las especialidades disponibles.
     * 
     * @return Lista de especialidades.
     */
    @Operation(
        summary = "Obtener todas las especialidades",
        description = "Retorna una lista de todas las especialidades disponibles.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de especialidades obtenida con éxito",
                content = @Content(
                    schema = @Schema(implementation = Especialidad.class),
                    examples = @ExampleObject(value = "[{\"id\": 1, \"nombre\": \"Cardiología\", \"descripcion\": \"Especialidad en enfermedades del corazón\"}, {\"id\": 2, \"nombre\": \"Neurología\", \"descripcion\": \"Especialidad en enfermedades del sistema nervioso\"}]")
                )
            ),
            @ApiResponse(responseCode = "404", description = "No se encontraron especialidades",
                content = @Content(
                    examples = @ExampleObject(value = "{\"message\": \"No se encontraron especialidades.\"}")
                )
            )
        }
    )
    @GetMapping
    public List<Especialidad> getAllEspecialidades() {
        return especialidadService.findAll();
    }
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
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada",
                content = @Content(
                    examples = @ExampleObject(value = "{\"message\": \"Especialidad no encontrada.\"}")
                )
            )
        }
    )
    @GetMapping("/{id}")
    public Especialidad getEspecialidadById(
        @Parameter(description = "ID de la especialidad", required = true, example = "1")
        @PathVariable Long id
    ) {
        return especialidadService.getById(id);
    }
    @Operation(
        summary = "Crear una nueva especialidad",
        description = "Crea una nueva especialidad con los datos proporcionados.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la especialidad a crear",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Especialidad.class),
                examples = @ExampleObject(
                    value = "{\"nombre\": \"Ginecología\", \"descripcion\": \"Especialidad en salud femenina\"}"
                )
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Especialidad creada con éxito",
                content = @Content(
                    schema = @Schema(implementation = Especialidad.class),
                    examples = @ExampleObject(value = "{\"id\": 3, \"nombre\": \"Ginecología\", \"descripcion\": \"Especialidad en salud femenina\"}")
                )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                content = @Content(
                    examples = @ExampleObject(value = "{\"message\": \"Datos de la especialidad son incorrectos o incompletos.\"}")
                )
            )
        }
    )
    @PostMapping("/crear")
    public Especialidad createEspecialidad(
        @RequestBody Especialidad especialidad
    ) {
        return especialidadService.save(especialidad);
    }
    @Operation(
        summary = "Actualizar una especialidad por ID",
        description = "Actualiza los datos de una especialidad existente basada en su ID.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados de la especialidad",
            required = true,
            content = @Content(
                schema = @Schema(implementation = EspecialidadDTO.class),
                examples = @ExampleObject(value = "{\"nombre\": \"Cardiología Avanzada\", \"descripcion\": \"Especialidad avanzada en enfermedades del corazón\"}")
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Especialidad actualizada con éxito",
                content = @Content(
                    schema = @Schema(implementation = Especialidad.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"nombre\": \"Cardiología Avanzada\", \"descripcion\": \"Especialidad avanzada en enfermedades del corazón\"}")
                )
            ),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada",
                content = @Content(
                    examples = @ExampleObject(value = "{\"message\": \"Especialidad no encontrada.\"}")
                )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                content = @Content(
                    examples = @ExampleObject(value = "{\"message\": \"Datos inválidos para la actualización.\"}")
                )
            )
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
    @Operation(
        summary = "Eliminar una especialidad por ID",
        description = "Elimina una especialidad específica basada en su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Especialidad eliminada con éxito",
                content = @Content(
                    examples = @ExampleObject(value = "{\"message\": \"Especialidad eliminada con éxito.\"}")
                )
            ),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada",
                content = @Content(
                    examples = @ExampleObject(value = "{\"message\": \"Especialidad no encontrada.\"}")
                )
            )
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