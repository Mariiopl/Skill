package com.mpl.backend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
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

    // Getters and setters
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
        // Convertimos el rol en un listado de authorities
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
        return true; // Puedes implementar lógica de expiración si lo deseas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implementar según necesidades de seguridad
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implementar según necesidades de seguridad
    }

    @Override
    public boolean isEnabled() {
        return true; // Implementar según necesidades de seguridad
    }
}
