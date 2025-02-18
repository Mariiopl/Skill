package com.mpl.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
    
        Authentication authentication = this.authManager.authenticate(authDTO);
        User user = (User) authentication.getPrincipal();
    
        String token = this.jwtTokenProvider.generateToken(authentication);
    
        return new LoginResponse(user.getUsername(),
                user.getRole().strip(), // Ahora devuelve roles sin "ROLE_"
                token);
    }
    
}
