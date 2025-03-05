package com.mpl.backend.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mpl.backend.dto.PruebaDTO;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.model.Item;
import com.mpl.backend.model.Prueba;
import com.mpl.backend.repository.EspecialidadRepository;
import com.mpl.backend.repository.PruebaRepository;
import com.mpl.backend.repository.ItemRepository;

@Service
public class PruebaService {

    @Autowired
    private PruebaRepository pruebaRepository;
    @Autowired
    private ItemRepository itemRepository;
    
    // Aquí debes inyectar EspecialidadRepository también
    @Autowired
    private EspecialidadRepository especialidadRepository;


    public Prueba crearPrueba(PruebaDTO pruebaDTO) {
        Especialidad especialidad = especialidadRepository.findById(pruebaDTO.idEspecialidad())
        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        Prueba prueba = new Prueba();
        prueba.setEnunciado(pruebaDTO.enunciado());
        prueba.setPuntuacionMaxima(pruebaDTO.puntuacionMaxima());
        prueba.setEspecialidad(especialidad);
        return pruebaRepository.save(prueba);
    }  

    public Prueba save(Prueba prueba) {
        return pruebaRepository.save(prueba);
    }

    public Prueba getById(Long id) {
        return pruebaRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        pruebaRepository.deleteById(id);
    }

    public List<Prueba> findAll() {
        return pruebaRepository.findAll();
    }

    public Prueba updatePrueba(PruebaDTO pruebaDTO, Long id) {
        Prueba prueba = pruebaRepository.findById(id).orElse(null);
        prueba.setEnunciado(pruebaDTO.enunciado());
        prueba.setPuntuacionMaxima(pruebaDTO.puntuacionMaxima());
        prueba.setEspecialidad(especialidadRepository.findById(pruebaDTO.idEspecialidad()).orElse(null));
        return pruebaRepository.save(prueba);
    }
    public Prueba agregarItem(Long idPrueba, Long idItem) {
        Prueba prueba = pruebaRepository.findById(idPrueba).orElse(null);
        if (prueba == null) {
            throw new RuntimeException("Prueba no encontrada");
        }
    
        Item item = itemRepository.findById(idItem).orElse(null);
        if (item == null) {
            throw new RuntimeException("Item no encontrado");
        }
    
        item.setPrueba(prueba);
        itemRepository.save(item);
    
        return prueba;
    }
    public Prueba addItemsToPrueba(Long idPrueba, List<Item> items) {
        Prueba prueba = pruebaRepository.findById(idPrueba).orElse(null);
        if (prueba == null) {
            throw new RuntimeException("Prueba no encontrada");
        }
    
        for (Item item : items) {
            Item foundItem = itemRepository.findById(item.getIdItem()).orElse(null);
            if (foundItem == null) {
                throw new RuntimeException("Item no encontrado");
            }
    
            foundItem.setPrueba(prueba);
            itemRepository.save(foundItem);
        }
    
        return prueba;
    }
    
    

}

