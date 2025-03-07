package com.mpl.backend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El rol no puede ser nulo")
    private String role;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @Size(min = 5, max = 20, message = "El nombre de usuario debe tener entre 5 y 20 caracteres")
    private String username;

    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 6, max = 50, message = "La contraseña debe tener entre 6 y 50 caracteres")
    private String password;

    @NotNull(message = "El nombre no puede ser nulo")
    private String nombre;

    @NotNull(message = "Los apellidos no pueden ser nulos")
    private String apellidos;

    @NotNull(message = "El DNI no puede ser nulo")
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]{1}$", message = "El DNI debe tener el formato correcto (8 dígitos seguidos de una letra)")
    private String dni;

    @ManyToOne
    private Especialidad especialidad;

    // Constructor por defecto necesario para JPA y Spring Security
    public User() {
    }

    // Constructor para registrar un usuario
    public User(String username, String password, String dni) {
        this.username = username;
        this.password = password;
        this.dni = dni;
        this.role = "experto"; // rol por defecto
    }

    // Getters and setters (no cambia mucho de los anteriores)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    // Implementación de métodos de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + this.role); // Añadimos "ROLE_" para que coincida con el formato de Spring Security
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
