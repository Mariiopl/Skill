import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserRegistradoService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const loginData = sessionStorage.getItem('LOGIN');

    let token = '';
    if (loginData) {
      try {
        const parsedData = JSON.parse(loginData);
        token = parsedData.token; // Extrae el token correctamente
      } catch (error) {
        console.error('Error parsing LOGIN data from sessionStorage', error);
      }
    }

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getUserData(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/me`, { headers: this.getHeaders() });
  }
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Algo salió mal. Intenta de nuevo más tarde.';
    
    if (error.status === 400) {
      // Mostrar mensajes de validación si existen
      errorMessage = `Error de validación: ${JSON.stringify(error.error)}`;
    }
    
    return throwError(() => new Error(errorMessage));
  }
}
