import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Especialidad {
  idEspecialidad: number;
  nombre: string;
  codigo: string;
}

@Injectable({
  providedIn: 'root'
})
export class EspecialidadService {

  private apiUrl = 'http://localhost:8080/especialidad'; // URL del backend para especialidades

  constructor(private http: HttpClient) { }

  // Obtener todas las especialidades
  getEspecialidades(): Observable<Especialidad[]> {
    return this.http.get<Especialidad[]>(`${this.apiUrl}`);
  }

  // Agregar una nueva especialidad (con la ruta '/crear')
  addEspecialidad(especialidad: Especialidad): Observable<Especialidad> {
    return this.http.post<Especialidad>(`${this.apiUrl}/crear`, especialidad);
  }

  // Editar una especialidad existente
  updateEspecialidad(especialidad: Especialidad): Observable<Especialidad> {
    return this.http.put<Especialidad>(`${this.apiUrl}/${especialidad.idEspecialidad}`, especialidad);
  }

  // Eliminar una especialidad
  deleteEspecialidad(idEspecialidad: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${idEspecialidad}`);
  }
}
