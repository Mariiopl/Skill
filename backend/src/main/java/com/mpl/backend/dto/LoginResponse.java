package com.mpl.backend.dto;

import java.util.List;

public record LoginResponse(String username, List<String> roles, String token) {

}

