package com.mpl.backend.dto;

/*
DTO con la información necesaria para registrar un nuevo usuario en base de datos
{
    "username": "user6",    
    "password": "admin",    
    "password2": "admin",   
    "dni": "12345347W",     
    "nombre": "John",
    "apellidos": "Week"     
}
 */
public record UserRegisterDTO(String username, String password,  String password2, String dni, String nombre, String apellidos) {   
}
