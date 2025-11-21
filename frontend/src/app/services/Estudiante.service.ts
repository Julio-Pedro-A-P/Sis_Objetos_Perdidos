import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EstudianteService {

  private apiUrl = 'http://localhost:8080/api/estudiantes';

  constructor(private http: HttpClient) {}

  // ✔ Obtener datos completos del estudiante
  obtenerPerfil(usuarioId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/datos/${usuarioId}`);
  }

  // ✔ Completar datos del estudiante
  completarDatos(body: { usuarioId: number; facultad: string; telefono: string }): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/completar`, body);
  }
}
