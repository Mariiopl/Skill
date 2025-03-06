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
        description = "Registra un nuevo usuario en el sistema y devuelve los datos del usuario creado.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del usuario a registrar",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de Registro",
                    value = "{\n" +
                            "    \"username\": \"user6\",\n" +
                            "    \"password\": \"admin\",\n" +
                            "    \"password2\": \"admin\",\n" +
                            "    \"dni\": \"12345347W\",\n" +
                            "    \"nombre\": \"John\",\n" +
                            "    \"apellidos\": \"Week\"\n" +
                            "}"
                )
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de Respuesta",
                    value = "{\n" +
                            "    \"id\": 1,\n" +
                            "    \"username\": \"user6\",\n" +
                            "    \"role\": \"USER\",\n" +
                            "    \"especialidad\": {\n" +
                            "        \"idEspecialidad\": 2,\n" +
                            "        \"nombre\": \"Administración de Sistemas\"\n" +
                            "    }\n" +
                            "}"
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida - Datos incorrectos o usuario ya registrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/auth/register")
    public User save(@RequestBody UserRegisterDTO userDTO) {
        return this.userService.save(userDTO);
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Permite a un usuario autenticarse y obtener un token JWT.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciales de inicio de sesión",
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequest.class),
                examples = @ExampleObject(
                    value = "{\"username\": \"mario\", \"password\": \"admin\"}"
                )
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
                content = @Content(
                    schema = @Schema(implementation = LoginResponse.class),
                    examples = @ExampleObject(
                        value = "{ \"username\": \"mario\", \"role\": \"ADMIN\", \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\", \"especialidadId\": 2 }"
                    )
                )
            ),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
        }
    )
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginDTO) {
        Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        Authentication authentication = authManager.authenticate(authDTO);
        
        String token = jwtTokenProvider.generateToken(authentication);
        User user = (User) authentication.getPrincipal();

        return new LoginResponse(user.getUsername(), user.getRole(), token, user.getEspecialidad().getIdEspecialidad());
    }

    @Operation(
        summary = "Cerrar sesión",
        description = "Finaliza la sesión del usuario (solo respuesta de confirmación, el backend no almacena sesiones).",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cierre de sesión exitoso",
                content = @Content(
                    examples = @ExampleObject(value = "{ \"message\": \"Logout exitoso\" }")
                )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        }
    )
    @PostMapping("/auth/logout")
    public ResponseEntity<Map<String, String>> logout(
        @RequestHeader("Authorization") 
        @Parameter(description = "Token JWT de autenticación", required = true, example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") 
        String token
    ) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout exitoso");
        return ResponseEntity.ok(response);
    }
}
