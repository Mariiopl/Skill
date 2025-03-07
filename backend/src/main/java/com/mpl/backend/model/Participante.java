package com.mpl.backend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipante;

    // Validación: nombre no puede estar vacío
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    // Validación: apellidos no pueden estar vacíos
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    // Validación: centro no puede estar vacío ni tener más de 100 caracteres
    @NotBlank(message = "El centro es obligatorio")
    @Size(max = 100, message = "El nombre del centro no puede tener más de 100 caracteres")
    private String centro;

    // Relación con Especialidad, no puede ser nula
    @NotNull(message = "La especialidad es obligatoria")
    @ManyToOne
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;
    

    // Getters and Setters
    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
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

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }



}
