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
        // Filtra solo los expertos
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
        console.log('Especialidades cargadas:', data);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar las especialidades.';
      }
    });
  }

  openCreateModal(): void {
    this.isCreateModalOpen = true;
    // Reiniciar el formulario
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
  
}
