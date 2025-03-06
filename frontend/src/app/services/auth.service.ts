import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

interface LoginResponse {
  username: string;
  role: string;
  token: string;
  especialidadId: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/auth/login';
  private tokenKey = 'auth_token';
  private userKey = 'user';

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<boolean> {
    return this.http.post<LoginResponse>(this.apiUrl, { username, password }).pipe(
      map(response => {
        localStorage.setItem(this.tokenKey, response.token);
        localStorage.setItem(this.userKey, JSON.stringify({ username: response.username, especialidadId: response.especialidadId }));
        return true;
      }),
      catchError(err => {
        console.error('Login failed', err);
        return [false];
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem(this.tokenKey);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getUserEspecialidadId(): number | null {
    const user = JSON.parse(localStorage.getItem(this.userKey) || '{}');
    return user?.especialidadId || null;
  }
}