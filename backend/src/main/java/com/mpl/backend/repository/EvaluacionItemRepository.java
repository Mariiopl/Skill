package com.mpl.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpl.backend.model.EvaluacionItem;

public interface EvaluacionItemRepository extends JpaRepository<EvaluacionItem, Integer> {
}
