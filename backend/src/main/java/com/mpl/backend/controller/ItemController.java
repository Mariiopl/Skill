package com.mpl.backend.controller;

import com.mpl.backend.dto.ItemDTO;
import com.mpl.backend.model.Item;
import com.mpl.backend.service.ItemService;
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
@RequestMapping("/items")
@Tag(name = "Items", description = "Operaciones relacionadas con la gestión de ítems")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Obtener todos los items
    @Operation(
        summary = "Obtener todos los ítems",
        description = "Retorna una lista de todos los ítems disponibles."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ítems obtenida con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontraron ítems")
    })
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    // Obtener un item por ID
    @Operation(
        summary = "Obtener un ítem por ID",
        description = "Retorna un ítem específico basado en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem encontrado"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @GetMapping("/{id}")
    public Item getItemById(
        @Parameter(description = "ID del ítem", required = true, example = "1")
        @PathVariable Long id
    ) {
        return itemService.getById(id);
    }

    // Guardar un nuevo item
    @Operation(
        summary = "Crear un nuevo ítem",
        description = "Crea un nuevo ítem con los datos proporcionados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ítem creado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/crear")
    public ResponseEntity<Item> createItem(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del ítem a crear",
            required = true,
            content = @Content(
                schema = @Schema(implementation = ItemDTO.class),
                examples = @ExampleObject(
                    value = "{\"nombre\": \"Item 1\", \"descripcion\": \"Descripción del ítem 1\"}"
                )
            )
        )
        @RequestBody ItemDTO itemDTO
    ) {
        Item item = itemService.crearItem(itemDTO);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    // Eliminar un item por ID
    @Operation(
        summary = "Eliminar un ítem por ID",
        description = "Elimina un ítem específico basado en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteItem(
        @Parameter(description = "ID del ítem a eliminar", required = true, example = "1")
        @PathVariable Long id
    ) {
        itemService.deleteById(id);
    }

    // Actualizar un item por ID
    @Operation(
        summary = "Actualizar un ítem por ID",
        description = "Actualiza los datos de un ítem existente basado en su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem actualizado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados del ítem",
            required = true,
            content = @Content(
                schema = @Schema(implementation = ItemDTO.class),
                examples = @ExampleObject(
                    value = "{\"nombre\": \"Item 1 Actualizado\", \"descripcion\": \"Descripción actualizada del ítem 1\"}"
                )
            )
        )
        @RequestBody ItemDTO itemDTO,
        @Parameter(description = "ID del ítem a actualizar", required = true, example = "1")
        @PathVariable Long id
    ) {
        Item item = itemService.updateItem(itemDTO, id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}