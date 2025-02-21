import { Component, OnInit } from '@angular/core';
import { ExpertosService, Experto } from '../services/expertos.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-expertos',
  // Si usas componentes standalone (Angular 14+), asegúrate de incluir CommonModule
  standalone: true,
  imports: [CommonModule],
  templateUrl: './expertos.component.html',
  styleUrls: ['./expertos.component.css']
})
export class ExpertosComponent implements OnInit {
  expertos: Experto[] = [];

  constructor(private expertosService: ExpertosService) {}

  ngOnInit(): void {
    this.cargarExpertos();
  }

  cargarExpertos(): void {
    this.expertosService.getExpertos().subscribe({
      next: (data) => {
        this.expertos = data;
        console.log('Expertos cargados:', data);
      },
      error: (err) => {
        console.error('Error al cargar expertos', err);
      }
    });
  }
}
