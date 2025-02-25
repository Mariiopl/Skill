package com.mpl.backend.service;


import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mpl.backend.dto.UserRegisterDTO;
import com.mpl.backend.model.User;
import com.mpl.backend.repository.UserRepository;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.repository.EspecialidadRepository;


@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EspecialidadRepository especialidadRepository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, EspecialidadRepository especialidadRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.especialidadRepository = especialidadRepository;
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
        
        // Recuperar la especialidad si se ha enviado un ID
        if(userDTO.especialidadId() != null) {
            Optional<Especialidad> especialidadOpt = especialidadRepository.findById(userDTO.especialidadId());
            if(especialidadOpt.isPresent()){
                user.setEspecialidad(especialidadOpt.get());
            } else {
                // Manejar el caso en que no se encuentre la especialidad (lanzar excepción o dejar en null)
                throw new IllegalArgumentException("Especialidad no encontrada para el id: " + userDTO.especialidadId());
            }
        } else {
            user.setEspecialidad(null);
        }
        
        // Asigna un rol por defecto
        user.setRole("experto");
    
        return this.repository.save(user);
    }
    

    public User saveUser(User user) {
        return repository.save(user);
    }
    
}
