import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-expert-users',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './expert-users.component.html',
  styleUrls: ['./expert-users.component.css']
})
export class ExpertUsersComponent implements OnInit {
  experts: any[] = [];
  especialidades: any[] = [];
  error: string = '';
  isCreateModalOpen: boolean = false;
  isEditModalOpen: boolean = false;
  selectedExpert: any = {};

  // Objeto para crear un nuevo experto
  newExpert: any = {
    username: '',
    password: '',
    password2: '',
    nombre: '',
    apellidos: '',
    dni: '',
    idEspecialidad: null
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadExperts();
    this.loadEspecialidades();
  }

  loadExperts(): void {
    this.http.get<any[]>('http://localhost:8080/users').subscribe({
      next: (data) => {
        this.experts = data.filter(user => user.role === 'experto');
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar los expertos.';
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

  openCreateModal(): void {
    this.isCreateModalOpen = true;
    this.newExpert = {
      username: '',
      password: '',
      password2: '',
      nombre: '',
      apellidos: '',
      dni: '',
      idEspecialidad: null
    };
  }

  closeCreateModal(): void {
    this.isCreateModalOpen = false;
  }

  createExpert(): void {
    const expertToCreate = { 
      ...this.newExpert, 
      role: 'experto', 
      especialidadId: this.newExpert.idEspecialidad 
    };

    delete expertToCreate.idEspecialidad;

    this.http.post<any>('http://localhost:8080/auth/register', expertToCreate).subscribe({
      next: (user) => {
        this.experts.push(user);
        this.closeCreateModal();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al crear el experto.';
      }
    });
  }

  // Abre el modal de edición con los datos del experto seleccionado
  openEditModal(expert: any): void {
    this.selectedExpert = { ...expert };
    this.isEditModalOpen = true;
  }

  // Cierra el modal de edición
  closeEditModal(): void {
    this.isEditModalOpen = false;
  }

  // Envía los cambios del experto editado al backend
  updateExpert(): void {
    const updatedExpert = { 
      ...this.selectedExpert, 
      especialidadId: this.selectedExpert.idEspecialidad 
    };

    delete updatedExpert.idEspecialidad;

    this.http.put<any>(`http://localhost:8080/users/${updatedExpert.id}`, updatedExpert).subscribe({
      next: () => {
        const index = this.experts.findIndex(e => e.id === updatedExpert.id);
        if (index !== -1) this.experts[index] = updatedExpert;
        this.closeEditModal();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al actualizar el experto.';
      }
    });
  }
}
