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
  especialidades: any[] = []; // Array de especialidades (si es necesario)
  newPrueba: any = {
    enunciado: '',
    puntuacionMaxima: null,
    especialidadId: null
  };
  error: string = '';
  isCreateModalOpen: boolean = false; // Variable para mostrar/ocultar el modal
  isEditMode: boolean = false; // Variable para saber si estamos en modo edición o creación

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadPruebas(); // Cargar las pruebas desde la API
    this.loadEspecialidades(); // Cargar las especialidades (si es necesario)
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
    this.isEditMode = false; // Aseguramos que no estamos en modo de edición
    this.newPrueba = { enunciado: '', puntuacionMaxima: null, especialidadId: null }; // Limpiar los campos
  }

  // Cerrar el modal de creación/edición
  closeCreateModal(): void {
    this.isCreateModalOpen = false;
  }

  createPrueba(): void {
    this.http.post<any>('http://localhost:8080/pruebas/crear', this.newPrueba).subscribe({
      next: (data) => {
        this.pruebas.push(data); // Añadir la nueva prueba a la lista
        this.closeCreateModal(); // Cerrar el modal
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al crear la prueba.';
      }
    });
  }

  // Modificar una prueba existente
  editPrueba(id: number): void {
    this.isEditMode = true; // Establecer el modo de edición
    const prueba = this.pruebas.find((prueba) => prueba.idPrueba === id);
    if (prueba) {
      this.newPrueba = { ...prueba }; // Rellenar los campos del formulario con los datos de la prueba
      this.openCreateModal(); // Abrir el modal para editar la prueba
    }
  }

  updatePrueba(id: number): void {
    this.http.put<any>(`http://localhost:8080/pruebas/${id}`, this.newPrueba).subscribe({
      next: (data) => {
        const index = this.pruebas.findIndex((prueba) => prueba.idPrueba === id);
        if (index !== -1) {
          this.pruebas[index] = data; // Reemplazar la prueba actualizada
        }
        this.closeCreateModal(); // Cerrar el modal
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al actualizar la prueba.';
      }
    });
  }

  deletePrueba(id: number): void {
    this.http.delete(`http://localhost:8080/pruebas/${id}`).subscribe({
      next: () => {
        this.pruebas = this.pruebas.filter((prueba) => prueba.idPrueba !== id); // Eliminar de la lista
      },
      error: (err) => {
        console.error(err);
        this.error = 'Esta prueba pertecece a alguna prueba de evaluación. No se puede eliminar.';
      }
    });
  }
}
