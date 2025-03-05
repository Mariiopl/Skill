import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-expert-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './expert-users.component.html',
  styleUrls: ['./expert-users.component.css']
})
export class ExpertUsersComponent implements OnInit {
  experts: any[] = [];
  especialidades: any[] = [];
  error: string = '';
  isCreateModalOpen: boolean = false;
  isEditModalOpen: boolean = false;
  selectedExpert: any = {}; // Nuevo: Experto seleccionado para editar

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

 // Convierte el valor del select a número
 convertIdEspecialidad(value: any): void {
  this.newExpert.idEspecialidad = Number(value);
}

createExpert(): void {
  const expertToCreate = { 
    ...this.newExpert, 
    role: 'experto', 
    especialidadId: this.newExpert.idEspecialidad // Renombramos la clave
  };

  delete expertToCreate.idEspecialidad; // Eliminamos la clave incorrecta

  console.log("Datos a enviar:", expertToCreate);

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

  openEditModal(expert: any): void {
    this.selectedExpert = { ...expert }; // Clonar el objeto para evitar cambios en la tabla antes de guardar
    this.selectedExpert.especialidadId = expert.especialidad?.idEspecialidad; // Extraer el id de especialidad
    this.isEditModalOpen = true;
  }

  closeEditModal(): void {
    this.isEditModalOpen = false;
  }

  updateExpert(): void {
    const expertToUpdate = {
      ...this.selectedExpert,
      especialidadId: this.selectedExpert.especialidadId
    };

    delete expertToUpdate.especialidad; // Eliminamos la clave incorrecta para evitar conflictos

    this.http.put<any>(`http://localhost:8080/users/${this.selectedExpert.id}`, expertToUpdate).subscribe({
      next: (updatedExpert) => {
        const index = this.experts.findIndex(u => u.id === updatedExpert.id);
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
