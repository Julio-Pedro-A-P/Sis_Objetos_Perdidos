import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class NotificacionService {

  private apiUrl = 'http://localhost:8080/api/notificaciones';

  constructor(private http: HttpClient) {}

  obtenerNoLeidas(estudianteId: number) {
    return this.http.get<any[]>(`${this.apiUrl}/estudiante/${estudianteId}`);
  }

  marcarLeida(id: number) {
    return this.http.put(`${this.apiUrl}/${id}/leido`, {});
  }
}
