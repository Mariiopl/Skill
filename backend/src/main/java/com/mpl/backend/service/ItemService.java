package com.mpl.backend.service;

import com.mpl.backend.dto.ItemDTO;
import com.mpl.backend.model.Item;
import com.mpl.backend.model.Prueba;
import com.mpl.backend.repository.ItemRepository;
import com.mpl.backend.repository.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PruebaRepository pruebaRepository;

    // Crear un nuevo Item
    public Item crearItem(ItemDTO itemDTO) {
        // Buscar la prueba en la base de datos por su ID
        Prueba prueba = pruebaRepository.findById(itemDTO.idPrueba())
            .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));

        Item item = new Item();
        item.setDescripcion(itemDTO.descripcion());
        item.setPeso(itemDTO.peso());
        item.setGradosConsecuion(itemDTO.gradosConsecuion());
        item.setPrueba(prueba);
        return itemRepository.save(item);
    }

    // Guardar un item
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    // Obtener un item por ID
    public Item getById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    // Eliminar un item por ID
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    // Obtener todos los items
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
