package com.mpl.backend.dto;



public record LoginResponse(String username, String role, String token, Long especialidadId) {

}

