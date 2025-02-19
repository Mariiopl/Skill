package com.mpl.backend.service;

import com.mpl.backend.dto.EvaluacionItemDTO;
import com.mpl.backend.model.EvaluacionItem;
import com.mpl.backend.model.Evaluacion;
import com.mpl.backend.model.Item;
import com.mpl.backend.repository.EvaluacionItemRepository;
import com.mpl.backend.repository.EvaluacionRepository;
import com.mpl.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionItemService {

    @Autowired
    private EvaluacionItemRepository evaluacionItemRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private ItemRepository itemRepository;

    // Crear un nuevo EvaluacionItem
    public EvaluacionItem crearEvaluacionItem(EvaluacionItemDTO evaluacionItemDTO) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionItemDTO.idEvaluacion())
            .orElseThrow(() -> new RuntimeException("Evaluacion no encontrada"));

        Item item = itemRepository.findById(evaluacionItemDTO.idItem())
            .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        EvaluacionItem evaluacionItem = new EvaluacionItem();
        evaluacionItem.setValoracion(evaluacionItemDTO.valoracion());
        evaluacionItem.setExplicacion(evaluacionItemDTO.explicacion());
        evaluacionItem.setEvaluacion(evaluacion);
        evaluacionItem.setItem(item);

        return evaluacionItemRepository.save(evaluacionItem);
    }

    // Obtener todos los EvaluacionItems
    public List<EvaluacionItem> findAll() {
        return evaluacionItemRepository.findAll();
    }

    // Obtener un EvaluacionItem por ID
    public EvaluacionItem getById(Long id) {
        return evaluacionItemRepository.findById(id).orElse(null);
    }

    // Eliminar un EvaluacionItem por ID
    public void deleteById(Long id) {
        evaluacionItemRepository.deleteById(id);
    }
}
