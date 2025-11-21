import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SolicitudService } from '../../../services/SolicitudService';


@Component({
  selector: 'app-historial',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './historial.component.html',
  styleUrls: ['./historial.component.css']
})
export class HistorialComponent implements OnInit {

  historial: any[] = [];
  historialFiltrado: any[] = [];
  filtro: string = '';

  constructor(private solicitudService: SolicitudService) {}

  ngOnInit(): void {
    this.solicitudService.listarHistorial().subscribe({
      next: (data) => {
        this.historial = data;
        this.historialFiltrado = data;
      },
      error: (err) => console.error(err)
    });
  }

  filtrar() {
    const t = this.filtro.trim().toLowerCase();

    this.historialFiltrado = this.historial.filter(s =>
      s.objeto.nombre.toLowerCase().includes(t) ||
      s.estudiante.nombres.toLowerCase().includes(t) ||
      s.estudiante.codigo.toLowerCase().includes(t) ||
      s.estado.toLowerCase().includes(t)
    );
  }
}
