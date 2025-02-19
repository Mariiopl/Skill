package com.mpl.backend.controller;

import com.mpl.backend.dto.ItemDTO;
import com.mpl.backend.model.Item;
import com.mpl.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Obtener todos los items
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    // Obtener un item por ID
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    // Guardar un nuevo item
    @PostMapping("/crear")
    public ResponseEntity<Item> createItem(@RequestBody ItemDTO itemDTO) {
        Item item = itemService.crearItem(itemDTO);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    // Eliminar un item por ID
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteById(id);
    }
}
