package com.mpl.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mpl.backend.dto.PruebaDTO;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.model.Item;
import com.mpl.backend.model.Prueba;
import com.mpl.backend.repository.PruebaRepository;
import com.mpl.backend.repository.ItemRepository;
import com.mpl.backend.repository.EspecialidadRepository;

import jakarta.transaction.Transactional;

@Service
public class PruebaServiceImplementacion implements PruebaService {

    @Autowired
    private PruebaRepository repository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private EspecialidadRepository especialidadRepository;

    private static final String UPLOAD_DIR = "backend/uploads/";

    @Override
    public Prueba crearPrueba(PruebaDTO pruebaDTO) {
        Especialidad especialidad = especialidadRepository.findById(pruebaDTO.idEspecialidad())
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        Prueba prueba = new Prueba();
        prueba.setEnunciado(pruebaDTO.enunciado());
        prueba.setPuntuacionMaxima(pruebaDTO.puntuacionMaxima());
        prueba.setEspecialidad(especialidad);
        return repository.save(prueba);
    }

    @Override
    public Prueba save(Prueba prueba) {
        return repository.save(prueba);
    }

    @Override
    public Prueba getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Prueba> findAll() {
        return repository.findAll();
    }

    @Override
    public Prueba updatePrueba(PruebaDTO pruebaDTO, Long id) {
        Prueba prueba = repository.findById(id).orElse(null);
        prueba.setEnunciado(pruebaDTO.enunciado());
        prueba.setPuntuacionMaxima(pruebaDTO.puntuacionMaxima());
        prueba.setEspecialidad(especialidadRepository.findById(pruebaDTO.idEspecialidad()).orElse(null));
        return repository.save(prueba);
    }

    @Override
    public Prueba agregarItem(Long idPrueba, Long idItem) {
        Prueba prueba = repository.findById(idPrueba).orElse(null);
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

    @Override
    public Prueba addItemsToPrueba(Long idPrueba, List<Item> items) {
        Prueba prueba = repository.findById(idPrueba).orElse(null);
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

    @Override
    @Transactional
    public void savePdf(Long id, MultipartFile file) throws IOException {
        Prueba prueba = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String fileName = String.format("id-%d_%s.pdf", id, fechaActual);
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        prueba.setRuta(filePath.toString());
        repository.save(prueba);
    }
}