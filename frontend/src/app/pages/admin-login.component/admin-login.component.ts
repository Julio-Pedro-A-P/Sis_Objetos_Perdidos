import {Component, OnDestroy, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../../services/Auth.service';
import {FormsModule} from '@angular/forms';
@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit ,OnDestroy {
  correoInstitucional: string = '';
  contrasena: string = '';

  constructor(private authService: AuthService, private router: Router) {}


  iniciarSesionAdmin(): void {
    const credentials = {
      correoInstitucional: this.correoInstitucional,
      contrasena: this.contrasena
    };

    this.authService.login(credentials).subscribe({
      next: (res: any) => {
        console.log('Respuesta del backend (admin):', res);

        // 🔐 Si el backend devuelve token y rol
        if (res.token && res.rol?.toUpperCase() === 'ADMIN') {
          // Guardar token y rol (en caso de que el AuthService no lo haya hecho)
          localStorage.setItem('token', res.token);
          localStorage.setItem('rol', res.rol);

          // Redirigir al panel de administrador
          this.router.navigate(['/panel/admin']);
        } else if (res.rol?.toUpperCase() === 'ESTUDIANTE') {
          alert('No tienes permisos de administrador. Usa el login de estudiante.');
        } else {
          alert('Rol no reconocido o token inválido.');
        }
      },
      error: (err) => {
        console.error('Error al iniciar sesión (admin):', err);
        alert('Correo o contraseña incorrectos');
      }
    });
  }


  ngOnInit(): void {
    document.addEventListener('wheel', this.preventZoom, { passive: false });
    document.addEventListener('keydown', this.preventKeyZoom);
  }

  ngOnDestroy(): void {
    document.removeEventListener('wheel', this.preventZoom);
    document.removeEventListener('keydown', this.preventKeyZoom);
  }

  private preventZoom = (e: WheelEvent) => {
    if (e.ctrlKey) e.preventDefault();
  };

  private preventKeyZoom = (e: KeyboardEvent) => {
    if ((e.ctrlKey || e.metaKey) && ['+', '-', '0'].includes(e.key)) {
      e.preventDefault();
    }
  };

}
