import { Component, OnInit } from '@angular/core';
import { CommonModule, NgIf, NgForOf } from '@angular/common';
import { ParticipanteService, Participante } from '../services/participante.service';

@Component({
  selector: 'app-lista-participantes',
  standalone: true,
  imports: [CommonModule, NgIf, NgForOf],
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
        this.participantes = data;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}
