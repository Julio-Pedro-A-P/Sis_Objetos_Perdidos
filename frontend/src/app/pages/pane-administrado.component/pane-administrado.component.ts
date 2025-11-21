import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { ObjetoService } from '../../services/ObjetoService';
import { SolicitudService } from '../../services/SolicitudService';
import { UsuarioService } from '../../services/UsuarioService';

@Component({
  selector: 'app-pane-administrado',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    RouterOutlet
  ],
  templateUrl: './pane-administrado.component.html',
  styleUrls: ['./pane-administrado.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PaneAdministradoComponent implements OnInit {

  kpi = {
    pendientes: 0,
    entregados: 0,
    usuarios: 0
  };

  objetosActivos = 0;

  constructor(
    private router: Router,
    private objetoService: ObjetoService,
    private solicitudService: SolicitudService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.usuario.nombre = localStorage.getItem('nombreUsuario') || 'Administrador';

    this.cargarKPIs();
  }

  usuario = { nombre: '' };

  cargarKPIs(): void {

    // ✔ KPI Objetos activos
    this.objetoService.listarObjetos().subscribe({
      next: (data) => {
        this.objetosActivos = data.filter(obj => obj.estado === 'ACTIVO').length;
      }
    });

    // ✔ KPI Pendientes
    this.solicitudService.listarPendientes().subscribe({
      next: (data) => this.kpi.pendientes = data.length
    });

    // ✔ KPI Entregados
    this.solicitudService.listarAprobadas().subscribe({
      next: (data) => this.kpi.entregados = data.length
    });

    // ✔ KPI Usuarios
    this.usuarioService.contarUsuarios().subscribe({
      next: (total) => this.kpi.usuarios = total
    });
  }

  cerrarSesion(): void {
    localStorage.clear();
    sessionStorage.clear();
    this.router.navigate(['/login/admin']);
  }
}
