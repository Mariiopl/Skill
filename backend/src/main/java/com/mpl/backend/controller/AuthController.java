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

@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;



    @PostMapping("/auth/register")
    public User save(@RequestBody UserRegisterDTO userDTO){
        return this.userService.save(userDTO);
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginDTO) {
        // Autenticación
        Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
      System.err.println(loginDTO.username());
      System.err.println(loginDTO.password());
        Authentication authentication = authManager.authenticate(authDTO);
        System.err.println("adios");
        
        // Generación del token JWT
        String token = jwtTokenProvider.generateToken(authentication);
       
        // Obtener el usuario autenticado y devolver la respuesta
        String username = ((User) authentication.getPrincipal()).getUsername();
        String role = ((User) authentication.getPrincipal()).getRole();
        
        return new LoginResponse(username, role, token);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String token) {
        // Devolver un JSON en la respuesta
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout exitoso");
        
        return ResponseEntity.ok(response);
    }

}




