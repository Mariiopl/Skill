import { Component, OnInit } from '@angular/core';
import { CommonModule, NgIf, NgForOf } from '@angular/common';
import { ParticipanteService, Participante } from '../services/participante.service';
import { AuthService } from '../services/auth.service'; // Servicio de autenticación

@Component({
  selector: 'app-lista-participantes-id',
  standalone: true,
  imports: [CommonModule, NgIf, NgForOf],
  templateUrl: './lista-participantes-id.component.html',
  styleUrls: ['./lista-participantes-id.component.css']
})
export class ListaParticipantesIdComponent implements OnInit {
  participantes: Participante[] = [];
  idEspecialidad: number | null = null;

  constructor(private participanteService: ParticipanteService, private authService: AuthService) {}

  ngOnInit(): void {
    this.idEspecialidad = this.authService.getUserEspecialidadId(); // Obtener la idEspecialidad del usuario
    console.log('ID Especialidad del usuario:', this.idEspecialidad); // Agregar console.log aquí
    if (this.idEspecialidad !== null) {
      this.cargarParticipantesPorEspecialidad(this.idEspecialidad);
    }
  }

  cargarParticipantesPorEspecialidad(idEspecialidad: number): void {
    this.participanteService.getParticipantesEspecialidad(idEspecialidad).subscribe({
      next: (data) => {
        this.participantes = data;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}