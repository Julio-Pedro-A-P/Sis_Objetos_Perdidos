import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponet } from './pages/home.componet/home.componet';
import { LoginComponet } from './pages/login.componet/login.componet';
import { AdminLoginComponent } from './pages/admin-login.component/admin-login.component';

import { PaneAdministradoComponent } from './pages/pane-administrado.component/pane-administrado.component';
import { PanelEstudianteComponent } from './pages/panel-estudiante.component/panel-estudiante.component';

// COINCIDE EXACTAMENTE CON TUS CARPETAS
import { RegistrarComponent } from './pages/pane-administrado.component/registrar.component/registrar.component';
import { ValidarComponent } from './pages/pane-administrado.component/validar.component/validar.component';
import { HistorialComponent } from './pages/pane-administrado.component/historial.component/historial.component';
import { UsuariosComponent } from './pages/pane-administrado.component/usuarios.component/usuarios.component';

import { AuthGuard } from './guards/auth.guard';
import {BuscarObjetoComponent} from './pages/panel-estudiante.component/buscar-objeto.component/buscar-objeto.component';
import {MisSolicitudesComponent} from './pages/panel-estudiante.component/mis-solicitudes.component/mis-solicitudes.component';
import {PerfilEstudianteComponent} from './pages/panel-estudiante.component/perfil-estudiante.component/perfil-estudiante.component';


export const routes: Routes = [

  // HOME
  { path: '', component: HomeComponet },
  // LOGIN
  { path: 'login/estudiante', component: LoginComponet },
  { path: 'login/admin', component: AdminLoginComponent },
  // PANEL ADMIN
  {
    path: 'panel/admin', component: PaneAdministradoComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] },
    children: [
      { path: 'registrar', component: RegistrarComponent },
      { path: 'validar', component: ValidarComponent },
      { path: 'historial', component: HistorialComponent },
      { path: 'usuarios', component: UsuariosComponent },

      // ruta por defecto
      { path: '', redirectTo: 'registrar', pathMatch: 'full' }
    ]
  },

  // PANEL ESTUDIANTE
  {
    path: 'panel/estudiante', component: PanelEstudianteComponent, canActivate: [AuthGuard], data: { roles: ['ESTUDIANTE'] },
    children: [
      { path: 'buscarobjeto',component: BuscarObjetoComponent },
      { path: 'misSolicitudes', component: MisSolicitudesComponent },
      { path: 'perfil', component: PerfilEstudianteComponent},
      // Vista por defecto
      { path: '', redirectTo: 'buscarobjeto', pathMatch: 'full' }
    ]
  },
  // SI NO EXISTE LA RUTA
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}



