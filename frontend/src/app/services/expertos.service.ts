import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

// Añadir la interfaz de Especialidad
export interface Especialidad {
  idEspecialidad: number;
  nombre: string;
}

export interface Experto {
  id: number;
  nombre: string;
  role: string;
  especialidad: Especialidad;  // Agregar especialidad
}

@Injectable({
  providedIn: 'root'
})
export class ExpertosService {
  // URL del endpoint en el backend que devuelve solo expertos
  private apiUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) {}

  getExpertos(): Observable<Experto[]> {
    return this.http.get<Experto[]>(`${this.apiUrl}`).pipe(
      map((usuarios: Experto[]) => usuarios.filter(u => u.role === 'experto'))
    );
  }
}
