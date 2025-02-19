package com.mpl.backend.controller;

import com.mpl.backend.dto.EvaluacionItemDTO;
import com.mpl.backend.model.EvaluacionItem;
import com.mpl.backend.service.EvaluacionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluacion-items")
public class EvaluacionItemController {

    @Autowired
    private EvaluacionItemService evaluacionItemService;

    // Obtener todos los EvaluacionItems
    @GetMapping
    public List<EvaluacionItem> getAllEvaluacionItems() {
        return evaluacionItemService.findAll();
    }

    // Obtener un EvaluacionItem por ID
    @GetMapping("/{id}")
    public EvaluacionItem getEvaluacionItemById(@PathVariable Long id) {
        return evaluacionItemService.getById(id);
    }

    // Guardar un nuevo EvaluacionItem
    @PostMapping("/crear")
    public ResponseEntity<EvaluacionItem> createEvaluacionItem(@RequestBody EvaluacionItemDTO evaluacionItemDTO) {
        EvaluacionItem evaluacionItem = evaluacionItemService.crearEvaluacionItem(evaluacionItemDTO);
        return new ResponseEntity<>(evaluacionItem, HttpStatus.CREATED);
    }

    // Eliminar un EvaluacionItem por ID
    @DeleteMapping("/{id}")
    public void deleteEvaluacionItem(@PathVariable Long id) {
        evaluacionItemService.deleteById(id);
    }
}
