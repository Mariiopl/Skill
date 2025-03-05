package com.mpl.backend.controller;

import com.mpl.backend.dto.ParticipanteDTO;
import com.mpl.backend.model.Participante;
import com.mpl.backend.service.ParticipanteService;
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
@RequestMapping("/participantes")
@Tag(name = "Participantes", description = "Operaciones relacionadas con la gestión de participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    // Obtener todos los participantes
    @Operation(
        summary = "Obtener todos los participantes",
        description = "Retorna una lista de todos los participantes disponibles."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de participantes obtenida con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontraron participantes")
    })
    @GetMapping("/todos")
    public List<Participante> getAllParticipantes() {
        return participanteService.findAll();
    }

    // Obtener un participante por ID
    @Operation(
        summary = "Obtener un participante por ID",
        description = "Retorna un participante específico basado en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Participante encontrado"),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    @GetMapping("/{id}")
    public Participante getParticipanteById(
        @Parameter(description = "ID del participante", required = true, example = "1")
        @PathVariable Long id
    ) {
        return participanteService.getById(id);
    }

    // Guardar un nuevo participante
    @Operation(
        summary = "Crear un nuevo participante",
        description = "Crea un nuevo participante con los datos proporcionados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Participante creado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/crear")
    public ResponseEntity<Participante> createParticipante(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del participante a crear",
            required = true,
            content = @Content(
                schema = @Schema(implementation = ParticipanteDTO.class),
                examples = @ExampleObject(
                    value = "{\"nombre\": \"Juan\", \"apellido\": \"Pérez\", \"email\": \"juan@example.com\"}"
                )
            )
        )
        @RequestBody ParticipanteDTO participanteDTO
    ) {
        Participante participante = participanteService.crearParticipante(participanteDTO);
        return new ResponseEntity<>(participante, HttpStatus.CREATED);
    }

    // Eliminar un participante por ID
    @Operation(
        summary = "Eliminar un participante por ID",
        description = "Elimina un participante específico basado en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Participante eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteParticipante(
        @Parameter(description = "ID del participante a eliminar", required = true, example = "1")
        @PathVariable Long id
    ) {
        participanteService.deleteById(id);
    }

    // Actualizar un participante por ID
    @Operation(
        summary = "Actualizar un participante por ID",
        description = "Actualiza los datos de un participante existente basado en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Participante actualizado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Participante> updateParticipante(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados del participante",
            required = true,
            content = @Content(
                schema = @Schema(implementation = ParticipanteDTO.class),
                examples = @ExampleObject(
                    value = "{\"nombre\": \"Juan\", \"apellido\": \"Pérez\", \"email\": \"juan@example.com\"}"
                )
            )
        )
        @RequestBody ParticipanteDTO participanteDTO,
        @Parameter(description = "ID del participante a actualizar", required = true, example = "1")
        @PathVariable Long id
    ) {
        Participante participante = participanteService.updateParticipante(participanteDTO, id);
        return new ResponseEntity<>(participante, HttpStatus.OK);
    }
}