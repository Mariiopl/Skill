package com.mpl.backend.dto;
// {
//     "descripcion": "Item de Prueba en Cardiología",
//     "peso": 10,
//     "gradosConsecuion": 5,
//     "idPrueba": 1
//   }
  
public record ItemDTO(String descripcion, Integer peso, Integer gradosConsecuion, Long idPrueba) {

}
