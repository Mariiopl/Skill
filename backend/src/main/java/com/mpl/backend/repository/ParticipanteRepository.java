package com.mpl.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpl.backend.model.Participante;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    
}
