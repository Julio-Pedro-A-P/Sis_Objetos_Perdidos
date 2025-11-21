import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ObjetoResponse, ObjetoService } from '../../../services/ObjetoService';

@Component({
  selector: 'app-registrar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registrar.component.html',
  styleUrls: ['./registrar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegistrarComponent implements OnInit {

  objetos: ObjetoResponse[] = [];

  objeto = {
    id: null as number | null,
    nombre: '',
    descripcion: '',
    lugarEncontrado: '',
    fecha: '',
    categoria: 'OTRO',
    imagenes: [] as File[]
  };

  imagenesSeleccionadas: File[] = [];
  imagenesPreview: string[] = [];
  editando = false;

  constructor(private objetoService: ObjetoService) {}

  ngOnInit(): void {
    this.cargarObjetos();
  }

  cargarObjetos(): void {
    this.objetoService.listarObjetos().subscribe({
      next: (data) => (this.objetos = data),
      error: (err) => console.error(' Error al listar objetos', err)
    });
  }

  /** =============================
   🖼️ COMPRESIÓN AUTOMÁTICA
   ============================= */
  resizeImage(file: File, maxWidth = 800, maxHeight = 800): Promise<File> {
    return new Promise((resolve) => {
      const img = new Image();
      const reader = new FileReader();

      reader.onload = (e: any) => {
        img.src = e.target.result;
      };

      img.onload = () => {
        const canvas = document.createElement('canvas');
        let width = img.width;
        let height = img.height;

        // Reducción proporcional
        if (width > maxWidth) {
          height = (maxWidth * height) / width;
          width = maxWidth;
        }
        if (height > maxHeight) {
          width = (maxHeight * width) / height;
          height = maxHeight;
        }

        canvas.width = width;
        canvas.height = height;

        const ctx = canvas.getContext('2d');
        ctx!.drawImage(img, 0, 0, width, height);

        canvas.toBlob((blob) => {
          resolve(new File([blob!], file.name, {
            type: 'image/jpeg',
            lastModified: Date.now()
          }));
        }, 'image/jpeg', 0.8);
      };

      reader.readAsDataURL(file);
    });
  }

  // SELECCIÓN DE IMÁGENES
  async onFilesSelected(event: any): Promise<void> {
    const files: FileList = event.target.files;
    this.imagenesSeleccionadas = [];
    this.imagenesPreview = [];

    for (let file of Array.from(files)) {
      const compressed = await this.resizeImage(file); // ← COMPRESIÓN
      this.imagenesSeleccionadas.push(compressed);

      const reader = new FileReader();
      reader.onload = () => this.imagenesPreview.push(reader.result as string);
      reader.readAsDataURL(compressed);
    }
  }

   //REGISTRAR / EDITAR
  registrarObjeto(): void {
    const formData = new FormData();
    formData.append('nombre', this.objeto.nombre);
    formData.append('descripcion', this.objeto.descripcion);
    formData.append('lugarEncontrado', this.objeto.lugarEncontrado);
    formData.append('fecha', this.objeto.fecha);
    formData.append('categoria', this.objeto.categoria);

    if (this.objeto.id) formData.append('id', this.objeto.id.toString());

    this.imagenesSeleccionadas.forEach((img) =>
      formData.append('imagenes', img)
    );

    const req = this.objeto.id
      ? this.objetoService.editarObjeto(formData, this.objeto.id)
      : this.objetoService.registrarObjeto(formData);

    req.subscribe({
      next: () => {
        alert(this.objeto.id ? '✔ Objeto actualizado' : '✔ Objeto registrado');
        this.cargarObjetos();
        this.resetForm();
      },
      error: (err) => {
        console.error('Error al guardar', err);
        alert('Error al guardar el objeto');
      }
    });
  }


   //CARGAR PARA EDITAR

  editar(objeto: any): void {
    this.objeto = {
      id: objeto.id,
      nombre: objeto.nombre,
      descripcion: objeto.descripcion,
      lugarEncontrado: objeto.lugarEncontrado,
      fecha: objeto.fecha,
      categoria: objeto.categoria,
      imagenes: []
    };

    this.editando = true;

    this.imagenesPreview = objeto.fotos
      ? objeto.fotos.map((f: string) => `http://localhost:8080/uploads/${f}`)
      : [];
  }

   //REINICIAR FORM
  resetForm(): void {
    this.objeto = {
      id: null,
      nombre: '',
      descripcion: '',
      lugarEncontrado: '',
      fecha: '',
      categoria: 'OTRO',
      imagenes: []
    };

    this.imagenesSeleccionadas = [];
    this.imagenesPreview = [];
    this.editando = false;
  }
}
