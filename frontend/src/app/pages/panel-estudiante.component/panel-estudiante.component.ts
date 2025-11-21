import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../services/Auth.service';
import {NotificacionService} from '../../services/NotificacionService';

@Component({
  selector: 'app-panel-estudiante',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    RouterOutlet
  ],
  templateUrl: './panel-estudiante.component.html',
  styleUrls: ['./panel-estudiante.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PanelEstudianteComponent implements OnInit {
  notificacion: any = null;
  mostrarModal = false;
  usuario = { nombre: '' };
  estudianteId = Number(localStorage.getItem('estudianteId'));


  constructor(
    private authService: AuthService,
    private router: Router,
    private notifService: NotificacionService
  ) {}

  ngOnInit(): void {
    this.usuario.nombre = this.authService.getNombre() || 'Estudiante';
    this.notifService.obtenerNoLeidas(this.estudianteId).subscribe(n => {
      if (n.length > 0) {
        this.notificacion = n[0];
        this.mostrarModal = true;
      }
    });

  }
  cerrar() {
    this.mostrarModal = false;
    this.notifService.marcarLeida(this.notificacion.id).subscribe();
  }

  // CERRAR SESIÓN
  cerrarSesion(): void {
    localStorage.clear();
    sessionStorage.clear();
    this.router.navigate(['/login/estudiante']);
  }
}
