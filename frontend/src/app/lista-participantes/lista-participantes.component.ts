import { Component, OnInit } from '@angular/core';
import { CommonModule, NgIf, NgForOf } from '@angular/common';
import { ParticipanteService, Participante } from '../services/participante.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-lista-participantes',
  standalone: true,
  imports: [CommonModule, NgIf, NgForOf, RouterLink],
  templateUrl: './lista-participantes.component.html',
  styleUrls: ['./lista-participantes.component.css']
})
export class ListaParticipantesComponent implements OnInit {
  participantes: Participante[] = [];

  constructor(private participanteService: ParticipanteService) {}

  ngOnInit(): void {
    this.cargarParticipantes();
  }

  cargarParticipantes(): void {
    this.participanteService.getParticipantes().subscribe({
      next: (data) => {
        console.log('Participantes cargados:', data);
        this.participantes = data;
      },
      error: (err) => {
        console.error('Error al obtener participantes:', err);
      }
    });
  }
}
