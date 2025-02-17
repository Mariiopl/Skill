package com.mpl.backend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpl.backend.model.Participante;
import com.mpl.backend.repository.ParticipanteRepository;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    public Participante save(Participante participante) {
        return participanteRepository.save(participante);
    }

    public Participante getById(Integer id) {
        return participanteRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        participanteRepository.deleteById(id);
    }

    public List<Participante> findAll() {
        return participanteRepository.findAll();
    }
}

