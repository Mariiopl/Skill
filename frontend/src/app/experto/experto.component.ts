import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EspecialidadService,Especialidad } from '../services/especialidad.service';


@Component({
  selector: 'app-experto',
  imports: [CommonModule],
  templateUrl: './experto.component.html',
  styleUrl: './experto.component.css'
})
export class ExpertoComponent implements OnInit { 
  especialidades: Especialidad[] = [];

  constructor(private especialidadService: EspecialidadService) {}

  ngOnInit(): void {
    this.loadEspecialidades();
  }

  // Cargar todas las especialidades
  loadEspecialidades(): void {
    this.especialidadService.getEspecialidades().subscribe(data => {
      this.especialidades = data;
    });
  }
}

