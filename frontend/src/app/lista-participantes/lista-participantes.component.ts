import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Participante, ParticipanteService } from '../services/participante.service';

@Component({
  selector: 'app-lista-participantes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-participantes.component.html',
  styleUrls: ['./lista-participantes.component.css']
})
export class ListaParticipantesComponent implements OnInit {
  participantes: Participante[] = [];

  constructor(private participanteService: ParticipanteService) { }

  ngOnInit(): void {
    this.cargarParticipantes();
  }

  cargarParticipantes(): void {
    this.participanteService.getParticipantes().subscribe({
      next: (data) => {
        console.log('Datos recibidos:', data);
        this.participantes = data;
      },
      error: (err) => {
        console.error('Error al cargar participantes', err);
      }
    });
  }
}
