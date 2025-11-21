import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth'; // 🔗 Endpoint backend

  constructor(private http: HttpClient) {}

  // LOGIN → guarda token y rol en LocalStorage
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
      tap((response: any) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('rol', response.rol);
          localStorage.setItem('nombreUsuario', response.nombre);
          localStorage.setItem('correo', response.correo);
          localStorage.setItem('usuarioId', response.id);
        }
      })
    );
  }

  // 🔹REGISTRO
  register(usuario: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, usuario);
  }
  //  OBTENER TOKEN
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  //  OBTENER ROL
  getRol(): string | null {
    return localStorage.getItem('rol');
  }
  //  OBTENER NOMBRE
  getNombre(): string | null {
    return localStorage.getItem('nombreUsuario');

  }
  //  CERRAR SESIÓN
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('rol');
  }
  getDatosEstudiante(usuarioId: number) {
    return this.http.get<any>(`http://localhost:8080/api/estudiantes/datos/${usuarioId}`);
  }

}
