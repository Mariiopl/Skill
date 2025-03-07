import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PruebasService {
  private apiUrl = 'http://localhost:8080/pruebas';

  constructor(private http: HttpClient) {}


  viewPdf(ruta: string): void {
    window.open(ruta, '_blank');
  }
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Algo salió mal. Intenta de nuevo más tarde.';
    
    if (error.status === 400) {
      // Mostrar los mensajes de validación si existen
      errorMessage = `Error de validación: ${JSON.stringify(error.error)}`;
    }
    
    return throwError(() => new Error(errorMessage));
  }
  uploadPdf(pruebaId: number, file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
  
    return this.http.post(`http://localhost:8080/pruebas/${pruebaId}/subir-pdf`, formData).pipe(
      catchError((error) => {
        console.error('Error en la subida del archivo', error);
        return throwError(() => new Error('Error al subir el archivo'));
      })
    );
  }
}
