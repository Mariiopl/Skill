import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PruebasService {
  private apiUrl = 'http://localhost:8080/pruebas';

  constructor(private http: HttpClient) {}

  uploadPdf(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<any>(`${this.apiUrl}/${id}/pdf`, formData);
  }

  viewPdf(ruta: string): void {
    window.open(ruta, '_blank');
  }
}
