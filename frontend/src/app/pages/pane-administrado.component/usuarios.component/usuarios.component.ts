import { UsuarioService } from '../../../services/UsuarioService';
import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-usuarios',
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']

})
export class UsuariosComponent implements OnInit {

  estudiantes: any[] = [];
  filtro: string = '';

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarEstudiantes();
  }

  cargarEstudiantes() {
    this.usuarioService.obtenerEstudiantes().subscribe({
      next: (data) => {
        this.estudiantes = data;
      },
      error: (err) => {
        console.error('Error al cargar estudiantes:', err);
      }
    });
  }

  filtrarEstudiantes() {
    const texto = this.filtro.toLowerCase();

    return this.estudiantes.filter(est =>
      est.nombres.toLowerCase().includes(texto) ||
      est.correoInstitucional.toLowerCase().includes(texto) ||
      est.codigo.includes(texto)
    );
  }
}
