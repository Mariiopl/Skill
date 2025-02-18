package com.mpl.backend.service;


import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mpl.backend.dto.UserRegisterDTO;
import com.mpl.backend.model.User;
import com.mpl.backend.repository.UserRepository;

@Service
public class UserService {
 private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return this.repository.findAll();  // Devuelve todos los usuarios
    }

    public User save(UserRegisterDTO userDTO) {
        if (userDTO.username() == null || userDTO.password() == null || userDTO.dni() == null) {
            throw new IllegalArgumentException("Username, password, and dni must not be null.");
        }
    
        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setDni(userDTO.dni());
        user.setNombre(userDTO.nombre());
        user.setApellidos(userDTO.apellidos());
        // Asigna un rol por defecto
        user.setRole("USER");
    
        return this.repository.save(user);
    }
    
}
