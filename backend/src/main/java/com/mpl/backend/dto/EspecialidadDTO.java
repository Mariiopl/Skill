package com.mpl.backend.dto;

// {
//     "nombre": "Neurología",
//     "codigo": "N01"
// }


public record EspecialidadDTO(
    @jakarta.validation.constraints.NotBlank(message = "El nombre es obligatorio") String nombre,
    @jakarta.validation.constraints.NotBlank(message = "El código es obligatorio") String codigo
) {
}