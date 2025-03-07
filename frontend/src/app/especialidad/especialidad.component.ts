import { Component, OnInit } from '@angular/core';
import { EspecialidadService, Especialidad } from '../services/especialidad.service';
import { FormsModule } from '@angular/forms'; // Importamos FormsModule para ngModel
import { CommonModule } from '@angular/common'; // Importamos CommonModule para ngClass

@Component({
  selector: 'app-especialidad',
  templateUrl: './especialidad.component.html',
  styleUrls: ['./especialidad.component.css'],
  standalone: true, // Permite trabajar sin módulos
  imports: [FormsModule, CommonModule] // Agregamos FormsModule y CommonModule aquí
})
export class EspecialidadComponent implements OnInit {
  especialidades: Especialidad[] = [];
  isModalOpen: boolean = false; // Para controlar la apertura del modal
  isEditMode: boolean = false; // Para saber si estamos en modo edición o creación
  currentEspecialidad: Especialidad = { idEspecialidad: 0, nombre: '', codigo: '' }; // Especialidad actual en el modal
  errorMessage: string = ''; // Para manejar el mensaje de error

  constructor(private especialidadService: EspecialidadService) {}

  ngOnInit(): void {
    this.loadEspecialidades();
  }

  // Cargar todas las especialidades
  loadEspecialidades(): void {
    this.especialidadService.getEspecialidades().subscribe(data => {
      this.especialidades = data;
    });
  }

  // Abrir modal para crear nueva especialidad
  openCreateModal(): void {
    this.isModalOpen = true;
    this.isEditMode = false; // Modo crear
    this.currentEspecialidad = { idEspecialidad: 0, nombre: '', codigo: '' }; // Limpiar los campos
    this.errorMessage = ''; // Limpiar cualquier error previo
  }

  // Abrir modal para editar especialidad
  openEditModal(especialidad: Especialidad): void {
    this.isModalOpen = true;
    this.isEditMode = true; // Modo editar
    this.currentEspecialidad = { ...especialidad }; // Cargar los datos de la especialidad en el modal
    this.errorMessage = ''; // Limpiar cualquier error previo
  }

  // Cerrar el modal
  closeModal(): void {
    this.isModalOpen = false;
  }

  // Guardar especialidad (crear o actualizar)
  saveEspecialidad(): void {
    this.errorMessage = '';  // Resetear el mensaje de error antes de cada intento de guardado

    if (this.isEditMode) {
      // Si estamos en modo edición, actualizar la especialidad
      this.especialidadService.updateEspecialidad(this.currentEspecialidad).subscribe({
        next: (data) => {
          const index = this.especialidades.findIndex(e => e.idEspecialidad === data.idEspecialidad);
          if (index !== -1) {
            this.especialidades[index] = data; // Actualizar la especialidad en la tabla
          }
          this.closeModal(); // Cerrar el modal después de la actualización
        },
        error: (error) => {
          this.errorMessage = error.message; // Mostrar mensaje de error
        }
      });
    } else {
      // Modo creación: crea un objeto nuevo sin idEspecialidad
      const especialidadNueva = {
        nombre: this.currentEspecialidad.nombre,
        codigo: this.currentEspecialidad.codigo
      };
      this.especialidadService.addEspecialidad(especialidadNueva as Especialidad).subscribe({
        next: (data) => {
          this.especialidades.push(data);
          this.closeModal();
        },
        error: (error) => {
          this.errorMessage = error.message; // Mostrar mensaje de error
        }
      });
    }
  }

  // Eliminar especialidad
  deleteEspecialidad(idEspecialidad: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar esta especialidad?')) {
      this.especialidadService.deleteEspecialidad(idEspecialidad).subscribe(() => {
        this.especialidades = this.especialidades.filter(e => e.idEspecialidad !== idEspecialidad);
      });
    }
  }
}
