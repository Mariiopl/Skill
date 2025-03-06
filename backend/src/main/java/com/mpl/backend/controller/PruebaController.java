package com.mpl.backend.controller;

import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import com.mpl.backend.dto.PruebaDTO;
import com.mpl.backend.model.Item;
import com.mpl.backend.model.Prueba;
import com.mpl.backend.repository.ItemRepository;
import com.mpl.backend.repository.PruebaRepository;
import com.mpl.backend.service.PruebaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/pruebas")
public class PruebaController {
     // Ruta base para guardar los archivos
     private static final String UPLOAD_DIR = "backend/uploads/";
 @Autowired
    private PruebaService pruebaService;
@Autowired
    private ItemRepository itemRepository;
@Autowired
    private PruebaRepository pruebaRepository;

    // Obtener todos los pruebas
    @GetMapping("/todos")
    public List<Prueba> getAllPruebas() {
        return pruebaService.findAll();
    }

    // Obtener un prueba por ID
    @GetMapping("/{id}")
    public Prueba getPruebaById(@PathVariable Long id) {
        return pruebaService.getById(id);
    }

    // Guardar un nuevo prueba
    @PostMapping("/crear")
    public ResponseEntity<Prueba> createPrueba(@RequestBody PruebaDTO pruebaDTO) {
        Prueba prueba = pruebaService.crearPrueba(pruebaDTO);
        return new ResponseEntity<>(prueba, HttpStatus.CREATED);
    }


    // Eliminar un prueba por ID
    @DeleteMapping("/{id}")
    public void deletePrueba(@PathVariable Long id) {
        pruebaService.deleteById(id);
    }

    // Actualizar un prueba por ID
    @PutMapping("/{id}")
    public ResponseEntity<Prueba> updatePrueba(@RequestBody PruebaDTO pruebaDTO, @PathVariable Long id) {
        Prueba prueba = pruebaService.updatePrueba(pruebaDTO, id);
        return new ResponseEntity<>(prueba, HttpStatus.OK);
    }
    // @PostMapping("/{idPrueba}/agregar-items")
    // public ResponseEntity<Prueba> addItemsToPrueba(@PathVariable Long idPrueba, @RequestBody List<Item> items) {
    //     Prueba prueba = pruebaService.getById(idPrueba);
    
    //     if (prueba == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //     }
    
    //     // Asociar los items a la prueba
    //     items.forEach(item -> item.setPrueba(prueba));
    
    //     // Guardar los items
    //     prueba.getItems().addAll(items);
    
    //     // Guardar la prueba con los nuevos items
    //     pruebaService.save(prueba);
    
    //     return ResponseEntity.status(HttpStatus.OK).body(prueba);
    // }
    

    @PostMapping("/{id}/pdf")
    public ResponseEntity<Void> uploadPdf(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            pruebaService.savePdf(id, file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/{idPrueba}/agregar-items")
    public ResponseEntity<String> addItemsToPrueba(@PathVariable Long idPrueba, @RequestBody List<Item> items) {
        System.out.println("📌 ID de la prueba: " + idPrueba);
        System.out.println("📌 Items recibidos: " + items);
    
        for (Item item : items) {
            System.out.println("🔎 Buscando item con ID: " + item.getIdItem());
    
            // Verificar si realmente está llegando el ID
            if (item.getIdItem() == null) {
                System.out.println("❌ El ID del item es NULL. Algo está mal en la petición.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID del item es NULL.");
            }
    
            // Buscar el item en la BD
            Item foundItem = itemRepository.findById(item.getIdItem()).orElse(null);
    
            if (foundItem == null) {
                System.out.println("❌ Item NO encontrado en la BD: " + item.getIdItem());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item no encontrado: " + item.getIdItem());
            }
    
            System.out.println("✅ Item encontrado en la BD: " + foundItem.getDescripcion());
    
            foundItem.setPrueba(pruebaRepository.findById(idPrueba).orElse(null));
            itemRepository.save(foundItem);
        }
    
        return ResponseEntity.ok("✅ Items asociados correctamente");
    }
    
    
    
}    


