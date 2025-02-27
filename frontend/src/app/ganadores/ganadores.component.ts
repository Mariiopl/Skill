import { Component, OnInit } from '@angular/core';
import { GanadoresService, Ganador } from '../services/ganadores.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ganadores',
  imports: [CommonModule],
  templateUrl: './ganadores.component.html',
  styleUrls: ['./ganadores.component.css']
})
export class GanadoresComponent implements OnInit {
  ganadores: Ganador[] = [];

  constructor(private ganadoresService: GanadoresService) { }

  ngOnInit(): void {
    this.cargarGanadores();
  }

  cargarGanadores(): void {
    this.ganadoresService.getGanadores().subscribe({
      next: (data) => this.ganadores = data,
      error: (err) => console.error('Error al obtener los ganadores:', err)
    });
  }
}
