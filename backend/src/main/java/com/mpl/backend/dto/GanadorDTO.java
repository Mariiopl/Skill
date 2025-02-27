package com.mpl.backend.dto;

public record GanadorDTO(
    Long idEvaluacion,
    Double notaFinal,
    String nombreParticipante,
    String apellidos,
    String nombrePrueba,
    String nombreEspecialidad
) {}
