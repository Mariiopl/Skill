package com.mpl.backend.dto;
// {
//     "nombre": "Paco",
//     "apellidos": "Pérez",
//     "centro": "Centro Ejemplo",
//     "idEspecialidad": 3
// }

public record ParticipanteDTO(String nombre, String apellidos, String centro, Long idEspecialidad) {
}