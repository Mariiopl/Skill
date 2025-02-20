import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Especialidad {
  idEspecialidad: number;
  nombre: string;
  codigo: string;
}

export interface Participante {
  idParticipante: number;
  nombre: string;
  apellidos: string;
  centro: string;
  especialidad: Especialidad;
}

@Injectable({
  providedIn: 'root'
})
export class ParticipanteService {
  private apiUrl = 'http://localhost:8080/participantes';

  constructor(private http: HttpClient) { }

  getParticipantes(): Observable<Participante[]> {
    console.log('Llamada a: ', `${this.apiUrl}/todos`);
    return this.http.get<Participante[]>(`${this.apiUrl}/todos`);
  }
  
  getParticipanteById(id: number): Observable<Participante> {
    return this.http.get<Participante>(`${this.apiUrl}/${id}`);
  }

}
