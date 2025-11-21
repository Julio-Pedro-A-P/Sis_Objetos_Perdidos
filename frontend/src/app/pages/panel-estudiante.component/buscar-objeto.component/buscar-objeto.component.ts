import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ObjetoService } from '../../../services/ObjetoService';
import { EstudianteService } from '../../../services/Estudiante.service';
import { SolicitudService } from '../../../services/SolicitudService';

import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-buscar-objeto',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './buscar-objeto.component.html',
  styleUrl: './buscar-objeto.component.css',
})
export class BuscarObjetoComponent implements OnInit {


  //  BÚSQUEDA Y FILTROS

  textoBusqueda: string = '';
  categoriaSeleccionada: string = 'TODOS';
  objetosFiltrados: any[] = [];


  // OBJETOS

  objetosPerdidos: any[] = [];


  // POPUP ÉXITO

  mostrarExito: boolean = false;


  // DATOS DEL ESTUDIANTE

  estudianteId: number | null = null;
  facultad: string = '';
  telefono: string = '';
  esPrimerRegistro: boolean = false;

  //  FORMULARIO DE RECLAMO

  modalReclamoVisible = false;
  lugarPerdida = '';
  fechaPerdida: string = '';
  colorMarca = '';
  detalles = '';

  //  MODAL CARRUSEL

  modalVisible = false;
  objetoSeleccionado: any = null;
  imagenActual = 0;


  // ESTADO PETICIÓN
  enviando = false;
  mensajeExito = '';
  mensajeError = '';

  constructor(
    private objetoService: ObjetoService,
    private estudianteService: EstudianteService,
    private solicitudService: SolicitudService
  ) {}

  ngOnInit(): void {
    this.cargarObjetos();
    this.cargarDatosEstudiante();
  }

   //CARGAR OBJETOS
  cargarObjetos(): void {
    this.objetoService.listarActivos().subscribe({
      next: (data) => {
        this.objetosPerdidos = data;
        this.objetosFiltrados = data;
      },
      error: (err) => console.error(err),
    });
  }

  //  CARGAR PERFIL ESTUDIANTE
  cargarDatosEstudiante(): void {
    const usuarioIdString =
      localStorage.getItem('idUsuario') || localStorage.getItem('id');

    const usuarioId = usuarioIdString ? Number(usuarioIdString) : null;

    if (!usuarioId) {
      console.warn('No se encontró idUsuario en localStorage');
      return;
    }

    this.estudianteService.obtenerPerfil(usuarioId).subscribe({
      next: (resp: any) => {
        this.estudianteId = resp.id;

        this.facultad = resp.facultad || '';
        this.telefono = resp.telefono || '';

        // ✔ SOLO pedir facultad/teléfono si REALMENTE están vacíos
        this.esPrimerRegistro =
          (!resp.facultad || resp.facultad.trim() === '') ||
          (!resp.telefono || resp.telefono.trim() === '');
      },
      error: (err) => console.error('Error al cargar perfil de estudiante', err),
    });
  }


  // FILTROS
  filtrarPorCategoria(cat: string): void {
    this.categoriaSeleccionada = cat;
    this.filtrarResultados();
  }

  filtrarResultados(): void {
    const texto = this.textoBusqueda.trim().toLowerCase();

    this.objetosFiltrados = this.objetosPerdidos.filter((obj) => {
      const coincideTexto =
        obj.nombre.toLowerCase().includes(texto) ||
        obj.categoria.toLowerCase().includes(texto);

      const coincideCategoria =
        this.categoriaSeleccionada === 'TODOS' ||
        obj.categoria === this.categoriaSeleccionada;

      return coincideTexto && coincideCategoria;
    });
  }


  // CARRUSEL

  abrirModal(obj: any): void {
    this.objetoSeleccionado = obj;
    this.imagenActual = 0;
    this.modalVisible = true;
  }

  cerrarModal(): void {
    this.modalVisible = false;
  }

  cambiarImagen(dir: number): void {
    const total = this.objetoSeleccionado?.fotos?.length || 0;
    if (!total) return;

    this.imagenActual = (this.imagenActual + dir + total) % total;
  }


  // MODAL RECLAMO
  abrirReclamo(obj: any): void {
    this.objetoSeleccionado = obj;
    this.modalReclamoVisible = true;

    this.mensajeError = '';
    this.mensajeExito = '';
    this.lugarPerdida = '';
    this.fechaPerdida = '';
    this.colorMarca = '';
    this.detalles = '';
  }

  cerrarModalReclamo(): void {
    this.modalReclamoVisible = false;
  }

  cerrarPopupExito(): void {
    this.mostrarExito = false;
  }


  // ENVIAR SOLICITUD
  enviarReclamo(): void {
    this.mensajeError = '';
    this.mensajeExito = '';

    const usuarioIdString =
      localStorage.getItem('idUsuario') || localStorage.getItem('id');
    const usuarioId = usuarioIdString ? Number(usuarioIdString) : null;

    if (!usuarioId) {
      this.mensajeError = 'No se encontró el id del usuario en localStorage.';
      return;
    }

    this.enviando = true;

    const completar$ = this.esPrimerRegistro
      ? this.estudianteService.completarDatos({
        usuarioId,
        facultad: this.facultad,
        telefono: this.telefono,
      })
      : this.estudianteService.obtenerPerfil(usuarioId);

    completar$
      .pipe(
        switchMap((respEstudiante: any) => {
          this.estudianteId = respEstudiante.id;

          const payload = {
            objetoId: this.objetoSeleccionado.id,
            estudianteId: this.estudianteId,
            fechaPerdida: this.fechaPerdida,
            lugarPerdida: this.lugarPerdida,
            colorMarca: this.colorMarca,
            detalles: this.detalles,
          };

          return this.solicitudService.crearSolicitud(payload);
        })
      )
      .subscribe({
        next: () => {
          this.enviando = false;
          this.modalReclamoVisible = false;

          this.lugarPerdida = '';
          this.colorMarca = '';
          this.detalles = '';
          this.fechaPerdida = '';

          this.mostrarExito = true;
        },

        error: (err) => {
          console.error(err);
          this.enviando = false;

          if (
            err.error &&
            err.error.message ===
            'Ya has enviado una solicitud para este objeto.'
          ) {
            this.mensajeError =
              'Ya has enviado una solicitud para este objeto.';
            return;
          }

          this.mensajeError = 'Ocurrió un error al enviar la solicitud.';
        },
      });
  }
}
