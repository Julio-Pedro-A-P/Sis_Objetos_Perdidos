import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';



export interface ObjetoResponse {
  id: number;
  nombre: string;
  descripcion: string;
  lugarEncontrado: string;
  fecha: string;
  categoria: string;
  fotos: string[];
  estado: string;
}

@Injectable({
  providedIn: 'root'
})
export class ObjetoService {
  private apiUrl: string = 'http://localhost:8080/api/objetos';

  constructor(private http: HttpClient) {}

  registrarObjeto(formData: FormData): Observable<ObjetoResponse> {
    return this.http.post<ObjetoResponse>(`${this.apiUrl}/registrar`, formData);
  }

  listarObjetos(): Observable<ObjetoResponse[]> {
    return this.http.get<ObjetoResponse[]>(`${this.apiUrl}/listar`);
  }

  editarObjeto(formData: FormData, id: number): Observable<ObjetoResponse> {
    return this.http.put<ObjetoResponse>(`${this.apiUrl}/editar/${id}`, formData);
  }
  // Obtener objetos activos (para el panel del estudiante)
  listarActivos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/activos`);

  }
 

}
