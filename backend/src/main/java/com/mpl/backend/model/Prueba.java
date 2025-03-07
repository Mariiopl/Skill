package com.mpl.backend.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Prueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrueba;

    @NotNull(message = "El enunciado no puede ser nulo")
    @Size(min = 5, max = 500, message = "El enunciado debe tener entre 5 y 500 caracteres")
    private String enunciado;

    @NotNull(message = "La puntuación máxima no puede ser nula")
    @Positive(message = "La puntuación máxima debe ser un valor positivo")
    private Integer puntuacionMaxima;

    private String ruta; // Campo para almacenar la ruta del PDF

    @ManyToOne
    @NotNull(message = "La especialidad no puede ser nula")
    private Especialidad especialidad;

    @OneToMany(mappedBy = "prueba", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("idItem ASC") // Opcional: para ordenar los items
    private List<Item> items;

    // Getters and Setters
    public Long getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Long idPrueba) {
        this.idPrueba = idPrueba;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Integer getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public void setPuntuacionMaxima(Integer puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad2) {
        this.especialidad = especialidad2;
    }
    
    public List<Item> getItems() {
        return items;
    }
    
    public void setItems(List<Item> items) {
        this.items = items;
    }

}

