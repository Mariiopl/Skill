package com.mpl.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mpl.backend.dto.UserRegisterDTO;
import com.mpl.backend.dto.UserUpdateDTO;
import com.mpl.backend.model.User;
import com.mpl.backend.repository.UserRepository;
import com.mpl.backend.model.Especialidad;
import com.mpl.backend.repository.EspecialidadRepository;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EspecialidadRepository especialidadRepository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder,
            EspecialidadRepository especialidadRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.especialidadRepository = especialidadRepository;
    }

    public Optional<User> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return this.repository.findAll(); // Devuelve todos los usuarios
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
        if (userDTO.especialidadId() != null) {
            Optional<Especialidad> especialidadOpt = especialidadRepository.findById(userDTO.especialidadId());
            if (especialidadOpt.isPresent()) {
                user.setEspecialidad(especialidadOpt.get());
            } else {
                // Manejar el caso en que no se encuentre la especialidad (lanzar excepción o
                // dejar en null)
                throw new IllegalArgumentException(
                        "Especialidad no encontrada para el id: " + userDTO.especialidadId());
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

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    // Método correcto para actualizar un usuario
    public User updateUser(Long id, UserUpdateDTO userDTO) {
        User existingUser = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    
        // No actualizamos la contraseña a menos que se pase un nuevo valor
        if (userDTO.username() != null) {
            existingUser.setUsername(userDTO.username());
        }
        if (userDTO.nombre() != null) {
            existingUser.setNombre(userDTO.nombre());
        }
        if (userDTO.apellidos() != null) {
            existingUser.setApellidos(userDTO.apellidos());
        }
        if (userDTO.dni() != null) {
            existingUser.setDni(userDTO.dni());
        }
    
        // Si se pasa un nuevo rol, lo actualizamos
        if (userDTO.role() != null) {
            existingUser.setRole(userDTO.role());
        }
    
        // Si se pasa una nueva especialidad, la actualizamos
        if (userDTO.especialidadId() != null) {
            Especialidad especialidad = especialidadRepository.findById(userDTO.especialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad not found"));
            existingUser.setEspecialidad(especialidad);
        }
    
        // No actualizamos la contraseña, solo la dejamos como está, salvo que la nueva contraseña se pase
    
        // Guardamos el usuario actualizado
        return repository.save(existingUser);
    }
    
    public User getUser(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    
}
