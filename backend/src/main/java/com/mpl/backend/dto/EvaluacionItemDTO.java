package com.mpl.backend.dto;
// {
//     "valoracion": 9,
//     "explicacion": "Excelente desempeño en la prueba.",
//     "idEvaluacion": 1,
//     "idItem": 1
  
//   }
  
public record EvaluacionItemDTO(Integer valoracion, String explicacion, Long idEvaluacion, Long idItem) {

}
