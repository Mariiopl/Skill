import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

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
    return this.http.get<Especialidad[]>(`${this.apiUrl}`).pipe(
      catchError(this.handleError)
    );
  }

  // Agregar una nueva especialidad (con la ruta '/crear')
  addEspecialidad(especialidad: Especialidad): Observable<Especialidad> {
    return this.http.post<Especialidad>(`${this.apiUrl}/crear`, especialidad).pipe(
      catchError(this.handleError)
    );
  }

  // Editar una especialidad existente
  updateEspecialidad(especialidad: Especialidad): Observable<Especialidad> {
    return this.http.put<Especialidad>(`${this.apiUrl}/${especialidad.idEspecialidad}`, especialidad).pipe(
      catchError(this.handleError)
    );
  }

  // Eliminar una especialidad
  deleteEspecialidad(idEspecialidad: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${idEspecialidad}`).pipe(
      catchError(this.handleError)
    );
  }

// Manejo de errores
private handleError(error: HttpErrorResponse) {
  let errorMessage = 'Algo salió mal. Intenta de nuevo más tarde.';
  
  if (error.status === 400) {
    // Si hay errores de validación, podemos extraerlos y devolverlos
    errorMessage = `Errores: ${JSON.stringify(error.error)}`;
  } else if (error.status === 404) {
    // Error 404: No encontrado
    errorMessage = 'La especialidad que intentas actualizar no existe.';
  } else if (error.status === 500) {
    // Error 500: Error interno del servidor 
    errorMessage = 'Error interno del servidor. Intenta de nuevo más tarde.';
  }

  return throwError(() => new Error(errorMessage));
}
}
