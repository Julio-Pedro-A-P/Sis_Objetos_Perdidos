import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {AuthService} from '../../services/Auth.service';

@Component({
  selector: 'app-login.componet',
  standalone: true,
  imports: [FormsModule, CommonModule,RouterLink],
  templateUrl: './login.componet.html',
  styleUrl: './login.componet.css',

})
export class LoginComponet implements OnInit, OnDestroy {


  correoInstitucional: string = '';
  contrasena: string = '';

  mostrarModalRegistro: boolean = false;

  nuevoUsuario = {
    correoInstitucional: '',
    nombres: '',
    contrasena: ''
  };

  constructor(private authService: AuthService, private router: Router) {}

  iniciarSesion(): void {

    const credentials = {
      correoInstitucional: this.correoInstitucional,
      contrasena: this.contrasena
    };

    this.authService.login(credentials).subscribe({
      next: (res: any) => {

        const rol = res.rol?.toUpperCase();

        // 🔹 Guardar datos básicos del usuario
        localStorage.setItem('token', res.token);
        localStorage.setItem('rol', rol);
        localStorage.setItem('idUsuario', String(res.id));
        localStorage.setItem('nombreUsuario', res.nombre || '');
        localStorage.setItem('correoUsuario', res.correo || '');

        // NUEVO: obtener estudianteId REAL
        this.authService.getDatosEstudiante(res.id).subscribe({
          next: (est: any) => {

            // Guardamos el ID del estudiante
            localStorage.setItem('estudianteId', String(est.id));

            // Ahora sí, redireccionar
            if (rol === 'ESTUDIANTE') {
              this.router.navigate(['/panel/estudiante']);
            } else if (rol === 'ADMIN') {
              alert('No tienes permisos de estudiante. Usa el login de administrador.');
            } else {
              alert('Rol no reconocido.');
            }
          },

          error: (err) => {
            console.error("Error obteniendo datos del estudiante:", err);
            alert("Error cargando datos de estudiante.");
          }
        });

      },

      error: (err) => {
        console.error('Error al iniciar sesión:', err);
        alert('Correo o contraseña incorrectos');
      }
    });

  }

  // MÉTODOS DE REGISTRO
  abrirModalRegistro(): void {
    this.mostrarModalRegistro = true;
  }

  cerrarModalRegistro(): void {
    this.mostrarModalRegistro = false;
  }

  registrarUsuario(): void {
    if (!this.nuevoUsuario.correoInstitucional || !this.nuevoUsuario.nombres || !this.nuevoUsuario.contrasena) {
      alert('Por favor completa todos los campos.');
      return;
    }

    this.authService.register(this.nuevoUsuario).subscribe({
      next: (res: any) => {
        alert(` ${res.mensaje || 'Usuario registrado correctamente.'}`);
        this.cerrarModalRegistro();
        this.nuevoUsuario = { correoInstitucional: '', nombres: '', contrasena: '' };
      },
      error: (err) => {
        console.error('Error al registrar usuario:', err);
        alert(' Error al registrar usuario.');
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
