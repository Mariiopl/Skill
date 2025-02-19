package com.mpl.backend.dto;
// {
//     "notaFinal": 8.5,
//     "idParticipante": 1,
//     "idPrueba": 2,
//     "idUser": 1
//   }
  
public record EvaluacionDTO(Double notaFinal, Long idParticipante, Long idPrueba, Long idUser) {

}
