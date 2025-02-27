import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GanadoresService {

  private apiUrl = 'http://localhost:8080/evaluaciones/ganadores'; // URL del backend

  constructor(private http: HttpClient) { }

  getGanadores(): Observable<Ganador[]> {
    return this.http.get<Ganador[]>(this.apiUrl);
  }
}

// Interfaz para tipado de datos
export interface Ganador {
  idEvaluacion: number;
  notaFinal: number;
  nombreParticipante: string;
  apellidos: string;
  nombrePrueba: string;
  nombreEspecialidad: string;
}
