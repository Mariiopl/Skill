package com.mpl.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpl.backend.model.Evaluacion;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

     @Query(value = """
        SELECT e FROM Evaluacion e
        WHERE e.notaFinal = (
            SELECT MAX(e2.notaFinal) 
            FROM Evaluacion e2 
            WHERE e2.prueba.idPrueba = e.prueba.idPrueba
        )
    """)
    List<Evaluacion> findTopScorersByTest();
}
