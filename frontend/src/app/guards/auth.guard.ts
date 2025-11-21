import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/Auth.service';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('Entrando al AuthGuard...');
    console.log('Token encontrado:', this.authService.getToken());
    const token = this.authService.getToken();

    //Detectar si la ruta es admin o estudiante
    const isAdminRoute = state.url.includes('/panel/admin');
    const isEstudianteRoute = state.url.includes('/panel/estudiante');

    //Si no hay token → redirigir al login correspondiente
    if (!token) {
      this.router.navigate([isAdminRoute ? '/login/admin' : '/login/estudiante']);
      return false;
    }

    try {
      const decoded: any = jwtDecode(token);

      // Verificar expiración del token
      if (Date.now() >= decoded.exp * 1000) {
        this.authService.logout();
        this.router.navigate([isAdminRoute ? '/login/admin' : '/login/estudiante']);
        return false;
      }

      // Detectar el rol del usuario
      let rolUsuario = '';
      if (decoded.rol) rolUsuario = decoded.rol;
      else if (decoded.role) rolUsuario = decoded.role;
      else if (decoded.authorities && Array.isArray(decoded.authorities)) {
        rolUsuario = decoded.authorities[0].replace('ROLE_', '');
      }

      rolUsuario = rolUsuario.toUpperCase();
      const rolesPermitidos = route.data['roles'] as Array<string>;

      // Si el rol no coincide
      if (rolesPermitidos && !rolesPermitidos.includes(rolUsuario)) {
        alert('No tienes permisos para acceder a esta página.');
        this.router.navigate([rolUsuario === 'ADMIN' ? '/panel/admin' : '/panel/estudiante']);

        return false;
      }
      //acceso Permitido
      return true;

    } catch (error) {
      console.error(' Error al validar token:', error);
      this.authService.logout();
      this.router.navigate([isAdminRoute ? '/login/admin' : '/login/estudiante']);
      return false;
    }
  }
}
