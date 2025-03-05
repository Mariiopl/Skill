package com.mpl.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mpl.backend.dto.LoginRequest;
import com.mpl.backend.dto.LoginResponse;
import com.mpl.backend.dto.UserRegisterDTO;
import com.mpl.backend.model.User;
import com.mpl.backend.security.JwtTokenProvider;
import com.mpl.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Autenticación", description = "Endpoints para registrar usuarios, iniciar sesión y cerrar sesión")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Este endpoint permite registrar un nuevo usuario en el sistema.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del usuario a registrar",
            required = true,
            content = @Content(
                schema = @Schema(implementation = UserRegisterDTO.class),
                examples = @ExampleObject(
                    value = "{\"username\": \"juan\", \"password\": \"123456\", \"email\": \"juan@example.com\"}"
                )
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/auth/register")
    public User save(@RequestBody UserRegisterDTO userDTO) {
        return this.userService.save(userDTO);
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Este endpoint permite a un usuario iniciar sesión y obtener un token JWT.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciales de inicio de sesión",
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequest.class),
                examples = @ExampleObject(
                    value = "{\"username\": \"juan\", \"password\": \"123456\"}"
                )
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginDTO) {
        // Autenticación
        Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        Authentication authentication = authManager.authenticate(authDTO);
        
        // Generación del token JWT
        String token = jwtTokenProvider.generateToken(authentication);

        // Obtener el usuario autenticado y devolver la respuesta
        String username = ((User) authentication.getPrincipal()).getUsername();
        String role = ((User) authentication.getPrincipal()).getRole();
        
        return new LoginResponse(username, role, token);
    }

    @Operation(
        summary = "Cerrar sesión",
        description = "Este endpoint permite a un usuario cerrar sesión. No requiere autenticación."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cierre de sesión exitoso"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/auth/logout")
    public ResponseEntity<Map<String, String>> logout(
        @RequestHeader("Authorization")
        @Parameter(
            description = "Token JWT de autenticación",
            required = true,
            example = "Bearer <token>"
        )
        String token
    ) {
        // Devolver un JSON en la respuesta
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout exitoso");
        
        return ResponseEntity.ok(response);
    }
}