import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';

interface User {
  id?: number;
  role?: string;
  username: string;
  password?: string;
  password2?: string;
  nombre: string;
  apellidos: string;
  dni: string;
  especialidadId?: any;
}

@Component({
  selector: 'app-expert-users',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './expert-users.component.html',
  styleUrls: ['./expert-users.component.css']
})
export class ExpertUsersComponent implements OnInit {
  experts: User[] = [];
  error: string = '';
  isCreateModalOpen: boolean = false;

  newExpert: User = {
    username: '',
    password: '',
    nombre: '',
    apellidos: '',
    dni: ''
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<User[]>('http://localhost:8080/users').subscribe({
      next: (users) => {
        this.experts = users.filter(user => user.role === 'experto');
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar los expertos.';
      }
    });
  }

  openCreateExpertModal(): void {
    this.isCreateModalOpen = true;
  }

  closeCreateModal(): void {
    this.isCreateModalOpen = false;
    this.newExpert = { username: '', password: '', nombre: '', apellidos: '', dni: '' };
  }

  createExpert(): void {
    const expertToCreate = { ...this.newExpert, role: 'experto' };
    this.http.post<User>('http://localhost:8080/auth/register', expertToCreate).subscribe({
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
