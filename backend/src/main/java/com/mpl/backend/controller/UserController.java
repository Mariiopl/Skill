package com.mpl.backend.controller;

import com.mpl.backend.dto.UserUpdateDTO;
import com.mpl.backend.model.User;
import com.mpl.backend.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();  // Devuelve la lista de usuarios
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User newUser) {
        newUser.setRole("experto");
        return userService.saveUser(newUser);  // Crea un nuevo usuario
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
    
}
