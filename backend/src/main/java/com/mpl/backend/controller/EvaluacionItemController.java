package com.mpl.backend.controller;

import com.mpl.backend.dto.EvaluacionItemDTO;
import com.mpl.backend.model.EvaluacionItem;
import com.mpl.backend.service.EvaluacionItemService;
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
@RequestMapping("/evaluacion-items")
@Tag(name = "Evaluación Items", description = "Operaciones relacionadas con la gestión de ítems de evaluación")
public class EvaluacionItemController {

    @Autowired
    private EvaluacionItemService evaluacionItemService;

    // Obtener todos los EvaluacionItems
    @Operation(
        summary = "Obtener todos los ítems de evaluación",
        description = "Retorna una lista de todos los ítems de evaluación disponibles."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ítems de evaluación obtenida con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontraron ítems de evaluación")
    })
    @GetMapping
    public List<EvaluacionItem> getAllEvaluacionItems() {
        return evaluacionItemService.findAll();
    }

    // Obtener un EvaluacionItem por ID
    @Operation(
        summary = "Obtener un ítem de evaluación por ID",
        description = "Retorna un ítem de evaluación específico basado en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem de evaluación encontrado"),
        @ApiResponse(responseCode = "404", description = "Ítem de evaluación no encontrado")
    })
    @GetMapping("/{id}")
    public EvaluacionItem getEvaluacionItemById(
        @Parameter(description = "ID del ítem de evaluación", required = true, example = "1")
        @PathVariable Long id
    ) {
        return evaluacionItemService.getById(id);
    }

    // Guardar un nuevo EvaluacionItem
    @Operation(
        summary = "Crear un nuevo ítem de evaluación",
        description = "Crea un nuevo ítem de evaluación con los datos proporcionados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ítem de evaluación creado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/crear")
    public ResponseEntity<EvaluacionItem> createEvaluacionItem(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del ítem de evaluación a crear",
            required = true,
            content = @Content(
                schema = @Schema(implementation = EvaluacionItemDTO.class),
                examples = @ExampleObject(
                    value = "{\"evaluacionId\": 1, \"itemId\": 1, \"nota\": 8.5}"
                )
            )
        )
        @RequestBody EvaluacionItemDTO evaluacionItemDTO
    ) {
        EvaluacionItem evaluacionItem = evaluacionItemService.crearEvaluacionItem(evaluacionItemDTO);
        return new ResponseEntity<>(evaluacionItem, HttpStatus.CREATED);
    }

    // Eliminar un EvaluacionItem por ID
    @Operation(
        summary = "Eliminar un ítem de evaluación por ID",
        description = "Elimina un ítem de evaluación específico basado en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem de evaluación eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Ítem de evaluación no encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteEvaluacionItem(
        @Parameter(description = "ID del ítem de evaluación a eliminar", required = true, example = "1")
        @PathVariable Long id
    ) {
        evaluacionItemService.deleteById(id);
    }

    // Actualizar un EvaluacionItem por ID
    @Operation(
        summary = "Actualizar un ítem de evaluación por ID",
        description = "Actualiza los datos de un ítem de evaluación existente basado en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem de evaluación actualizado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Ítem de evaluación no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionItem> updateEvaluacionItem(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados del ítem de evaluación",
            required = true,
            content = @Content(
                schema = @Schema(implementation = EvaluacionItemDTO.class),
                examples = @ExampleObject(
                    value = "{\"evaluacionId\": 1, \"itemId\": 1, \"nota\": 9.0}"
                )
            )
        )
        @RequestBody EvaluacionItemDTO evaluacionItemDTO,
        @Parameter(description = "ID del ítem de evaluación a actualizar", required = true, example = "1")
        @PathVariable Long id
    ) {
        EvaluacionItem evaluacionItem = evaluacionItemService.updateEvaluacionItem(evaluacionItemDTO, id);
        return new ResponseEntity<>(evaluacionItem, HttpStatus.OK);
    }
}