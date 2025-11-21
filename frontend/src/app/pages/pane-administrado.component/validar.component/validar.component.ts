import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SolicitudService } from '../../../services/SolicitudService';

@Component({
  selector: 'app-validar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './validar.component.html',
  styleUrls: ['./validar.component.css']
})
export class ValidarComponent implements OnInit {

  solicitudes: any[] = [];
  modalAbierto = false;
  solicitudSeleccionada: any = null;
  cargando = false;

  constructor(private solicitudService: SolicitudService) {}

  ngOnInit(): void {
    this.cargarPendientes();
  }

  cargarPendientes() {
    this.solicitudService.listarPendientes().subscribe({
      next: (data) => (this.solicitudes = data),
      error: (err) => console.error('Error al cargar solicitudes pendientes', err)
    });
  }

  abrirModal(solicitud: any) {
    this.solicitudSeleccionada = solicitud;
    this.modalAbierto = true;
  }

  cerrarModal() {
    this.modalAbierto = false;
    this.solicitudSeleccionada = null;
  }

  aprobarSolicitud(id: number) {
    this.cargando = true;

    this.solicitudService.aprobarSolicitud(id).subscribe({
      next: () => {
        alert('Solicitud aprobada correctamente');
        this.cargarPendientes();
        this.cerrarModal();
        this.cargando = false;
      },
      error: (err) => console.error(err)
    });
  }

  rechazarSolicitud(id: number) {
    this.cargando = true;

    this.solicitudService.rechazarSolicitud(id).subscribe({
      next: () => {
        alert('Solicitud rechazada');
        this.cargarPendientes();
        this.cerrarModal();
        this.cargando = false;
      },
      error: (err) => console.error(err)
    });
  }

}
