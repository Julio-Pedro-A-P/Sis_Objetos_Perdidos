import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SolicitudService {

  private apiUrl = 'http://localhost:8080/api/solicitudes';

  constructor(private http: HttpClient) {}

  crearSolicitud(body: {
    objetoId: number;
    estudianteId: number | null;
    lugarPerdida: string;
    colorMarca: string;
    detalles: string;
    fechaPerdida: string;
  }): Observable<any> {

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    });

    return this.http.post<any>(`${this.apiUrl}/crear`, body, { headers });
  }
  listarAprobadas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/aprobadas`);
  }


  getSolicitudesPorEstudiante(estudianteId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/estudiante/${estudianteId}`);
  }

  listarHistorial(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/historial`);
  }

  listarPendientes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/pendientes`);
  }

  aprobarSolicitud(id: number): Observable<any> {

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.put<any>(`${this.apiUrl}/${id}/aprobar`, {}, { headers });
  }

  rechazarSolicitud(id: number): Observable<any> {

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.put<any>(`${this.apiUrl}/${id}/rechazar`, {}, { headers });
  }

}
