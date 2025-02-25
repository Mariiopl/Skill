package com.mpl.backend.controller;

import com.mpl.backend.model.User;
import com.mpl.backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();  // Devuelve la lista de usuarios
    }
    @PostMapping
    public User createUser(@RequestBody User newUser) {
        newUser.setRole("experto");
        return userService.saveUser(newUser);
    }
}
