package com.mpl.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpl.backend.model.Evaluacion;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}
