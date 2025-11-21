import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EstudianteService } from '../../../services/Estudiante.service';
import { EstudianteResponse } from '../../../models/estudiante-response';

@Component({
  selector: 'app-perfil-estudiante',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfil-estudiante.component.html',
  styleUrls: ['./perfil-estudiante.component.css']
})
export class PerfilEstudianteComponent implements OnInit {

  perfil: EstudianteResponse = {
    id: 0,
    nombreUsuario: '',
    correoInstitucional: '',
    telefono: '',
    facultad: '',
    codigo: ''
  };
  perfilLoading = false;
  perfilError = '';


  constructor(private estudianteService: EstudianteService) {}

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cargarPerfil(): void {
    this.perfilLoading = true;

    const idUsuario = Number(localStorage.getItem('idUsuario'));

    if (!idUsuario) {
      console.error("No existe idUsuario en localStorage");
      this.perfilLoading = false;
      return;
    }

    this.estudianteService.obtenerPerfil(idUsuario).subscribe({
      next: (data: EstudianteResponse) => {
        this.perfil = data;
        this.perfilLoading = false;
      },
      error: (err) => {
        console.error(' Error al cargar perfil:', err);
        this.perfilError = 'No se pudo obtener el perfil.';
        this.perfilLoading = false;
      }
    });
  }
}
