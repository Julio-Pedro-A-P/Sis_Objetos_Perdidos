import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgClass } from '@angular/common';
import { SolicitudService } from '../../../services/SolicitudService';

@Component({
  selector: 'app-mis-solicitudes',
  standalone: true,
  imports: [CommonModule, NgClass],
  templateUrl: './mis-solicitudes.component.html',
  styleUrls: ['./mis-solicitudes.component.css']
})
export class MisSolicitudesComponent implements OnInit {

  solicitudes: any[] = [];

  constructor(private solicitudService: SolicitudService) {}

  ngOnInit(): void {
    // CORRECTO: ID del estudiante de localStorage
    const estudianteId = Number(localStorage.getItem('estudianteId'));

    if (!estudianteId) {
      console.error(" No existe estudianteId en localStorage");
      return;
    }

    console.log("🔍 Cargando solicitudes del estudiante:", estudianteId);

    this.solicitudService.getSolicitudesPorEstudiante(estudianteId)
      .subscribe({
        next: (data) => {
          console.log(" Solicitudes recibidas:", data);
          this.solicitudes = data;
        },
        error: (err) => {
          console.error(" Error obteniendo solicitudes:", err);
        }
      });
  }

}
