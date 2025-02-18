package com.mpl.backend.service;


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

    public User save(UserRegisterDTO userDTO) {
        User user = new User(userDTO.username(), passwordEncoder.encode(userDTO.password()), userDTO.dni());
        return this.repository.save(user);
    }
}
