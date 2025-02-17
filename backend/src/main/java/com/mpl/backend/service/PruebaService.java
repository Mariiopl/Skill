package com.mpl.backend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpl.backend.model.Prueba;
import com.mpl.backend.repository.PruebaRepository;

@Service
public class PruebaService {

    @Autowired
    private PruebaRepository pruebaRepository;

    public Prueba save(Prueba prueba) {
        return pruebaRepository.save(prueba);
    }

    public Prueba getById(Integer id) {
        return pruebaRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        pruebaRepository.deleteById(id);
    }

    public List<Prueba> findAll() {
        return pruebaRepository.findAll();
    }
}

