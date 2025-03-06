package com.mpl.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mpl.backend.dto.PruebaDTO;
import com.mpl.backend.model.Item;
import com.mpl.backend.model.Prueba;

public interface PruebaService {
    Prueba crearPrueba(PruebaDTO pruebaDTO);
    Prueba save(Prueba prueba);
    Prueba getById(Long id);
    void deleteById(Long id);
    List<Prueba> findAll();
    Prueba updatePrueba(PruebaDTO pruebaDTO, Long id);
    Prueba agregarItem(Long idPrueba, Long idItem);
    Prueba addItemsToPrueba(Long idPrueba, List<Item> items);
    void savePdf(Long id, MultipartFile file) throws IOException;
}