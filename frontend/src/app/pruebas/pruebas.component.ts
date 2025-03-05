import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-pruebas',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './pruebas.component.html',
  styleUrls: ['./pruebas.component.css']
})
export class PruebasComponent implements OnInit {
  pruebas: any[] = []; // Array de pruebas
  especialidades: any[] = []; // Array de especialidades
  newPrueba: any = {
    enunciado: '',
    puntuacionMaxima: null,
    especialidadId: null
  };
  error: string = '';
  isCreateModalOpen: boolean = false; // Variable para el modal de creación
  isEditModalOpen: boolean = false; // Variable para el modal de edición
  isEditMode: boolean = false; // Variable para saber si estamos en modo edición

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadPruebas();
    this.loadEspecialidades();
  }

  loadPruebas(): void {
    this.http.get<any[]>('http://localhost:8080/pruebas/todos').subscribe({
      next: (data) => {
        this.pruebas = data;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar las pruebas.';
      }
    });
  }

  loadEspecialidades(): void {
    this.http.get<any[]>('http://localhost:8080/especialidad').subscribe({
      next: (data) => {
        this.especialidades = data;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar las especialidades.';
      }
    });
  }

  // Abrir el modal para crear una nueva prueba
  openCreateModal(): void {
    this.isCreateModalOpen = true;
    this.isEditMode = false;
    this.newPrueba = { enunciado: '', puntuacionMaxima: null, especialidadId: null }; // Limpiar los campos
  }

  // Abrir el modal para editar una prueba existente
  openEditModal(): void {
    this.isEditModalOpen = true;
    this.isEditMode = true;
  }

  // Cerrar el modal de creación
  closeCreateModal(): void {
    this.isCreateModalOpen = false;
  }

  // Cerrar el modal de edición
  closeEditModal(): void {
    this.isEditModalOpen = false;
  }

  // Crear una nueva prueba
  createPrueba(): void {
    this.http.post<any>('http://localhost:8080/pruebas/crear', this.newPrueba).subscribe({
      next: (data) => {
        this.pruebas.push(data);
        this.closeCreateModal();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al crear la prueba.';
      }
    });
  }

  // Editar una prueba existente
  editPrueba(id: number): void {
    this.isEditMode = true; // Establecer el modo de edición
    const prueba = this.pruebas.find((prueba) => prueba.idPrueba === id);
  
    if (!prueba) {
      console.error(`No se encontró la prueba con ID ${id}`);
      return;
    }
  
    // Asignar correctamente los valores de la prueba al formulario de edición
    this.newPrueba = { ...prueba }; // Copiar los datos de la prueba
    this.newPrueba.idEspecialidad = prueba.especialidad.idEspecialidad; // Asignar correctamente la especialidad seleccionada
    
    // Abrir el modal de edición
    this.openEditModal();
  }
  

  // Actualizar una prueba existente
  updatePrueba(id: number): void {
    this.http.put<any>(`http://localhost:8080/pruebas/${id}`, this.newPrueba).subscribe({
      next: (data) => {
        const index = this.pruebas.findIndex((prueba) => prueba.idPrueba === id);
        if (index !== -1) {
          this.pruebas[index] = data;
        }
        this.closeEditModal();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al actualizar la prueba.';
      }
    });
  }

  // Eliminar una prueba
  deletePrueba(id: number): void {
    this.http.delete(`http://localhost:8080/pruebas/${id}`).subscribe({
      next: () => {
        this.pruebas = this.pruebas.filter((prueba) => prueba.idPrueba !== id);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Esta prueba pertenece a alguna prueba de evaluación. No se puede eliminar.';
      }
    });
  }
}
