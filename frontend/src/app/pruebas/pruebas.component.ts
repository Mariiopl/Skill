// filepath: /c:/xampp/htdocs/Skills/frontend/src/app/pruebas/pruebas.component.ts
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PruebasService } from '../services/pruebas.service'; // Importa el servicio

@Component({
  selector: 'app-pruebas',
  templateUrl: './pruebas.component.html',
  imports: [FormsModule, CommonModule],
  styleUrls: ['./pruebas.component.css'],
})
export class PruebasComponent implements OnInit {
  pruebas: any[] = [];
  especialidades: any[] = [];
  items: any[] = []; // Aquí se almacenarán los items disponibles
  newPrueba: any = { enunciado: '', puntuacionMaxima: 0, idEspecialidad: '' };
  error: string = '';
  isCreateModalOpen: boolean = false;
  isEditModalOpen: boolean = false;
  selectedFile: File | null = null; // Variable para almacenar el archivo seleccionado
  errorMessage: string = '';

  constructor(private http: HttpClient, private pruebasService: PruebasService) {} // Inyecta el servicio

  ngOnInit(): void {
    this.loadPruebas();
    this.loadEspecialidades();
    this.loadItems(); // Cargar los items existentes al inicializar
  }

  // Método para cargar las pruebas desde la API
  loadPruebas(): void {
    this.http.get<any[]>('http://localhost:8080/pruebas/todos').subscribe({
      next: (data) => {
        this.pruebas = data;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar las pruebas.';
      },
    });
  }

  // Método para cargar las especialidades desde la API
  loadEspecialidades(): void {
    this.http.get<any[]>('http://localhost:8080/especialidad').subscribe({
      next: (data) => {
        this.especialidades = data;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar las especialidades.';
      },
    });
  }

  // Método para cargar los items desde la API
  loadItems(): void {
    this.http.get<any[]>('http://localhost:8080/items').subscribe({
      next: (data) => {
        this.items = data; // Asignar los items a la variable 'items'
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar los items.';
      },
    });
  }

  // Método para abrir el modal de creación de prueba
  openCreateModal(): void {
    this.isCreateModalOpen = true;
  }

  // Método para cerrar el modal de creación de prueba
  closeCreateModal(): void {
    this.isCreateModalOpen = false;
  }

  // Método para crear una nueva prueba
  createPrueba(): void {
    if (
      !this.newPrueba.enunciado ||
      !this.newPrueba.puntuacionMaxima ||
      !this.newPrueba.idEspecialidad
    ) {
      this.error = 'Por favor, complete todos los campos.';
      return;
    }

    this.http
      .post('http://localhost:8080/pruebas/crear', this.newPrueba)
      .subscribe({
        next: () => {
          this.loadPruebas(); // Recargar la lista de pruebas
          this.closeCreateModal(); // Cerrar el modal
        },
        error: (err) => {
          console.error(err);
          this.error = 'Error al crear la prueba.';
        },
      });
  }

  // Método para asociar un item a una prueba
  addItemToPrueba(prueba: any): void {
    if (!prueba.selectedItemId) {
      alert('Selecciona un item antes de asociarlo.');
      return;
    }

    const itemSeleccionado = this.items.find(
      (item) => item.idItem === prueba.selectedItemId
    );
    if (!itemSeleccionado) {
      alert('Item no encontrado.');
      return;
    }

    const payload = [itemSeleccionado]; // Aquí estás enviando el item seleccionado en un arreglo.
    this.http
      .post(
        `http://localhost:8080/pruebas/${prueba.idPrueba}/agregar-items`,
        payload,
        {
          headers: { 'Content-Type': 'application/json' },
        }
      )
      .subscribe({
        next: () => {
          console.log('Item asociado correctamente');
        },
        error: (err) => {
          console.error('Error en la asociación', err);
          alert('Error en la asociación, revisa la consola del navegador.');
        },
      });
  }

  // Método para editar una prueba
  editPrueba(idPrueba: number): void {
    this.http.get<any>(`http://localhost:8080/pruebas/${idPrueba}`).subscribe({
      next: (data) => {
        this.newPrueba = data;
        this.isEditModalOpen = true;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar la prueba para editar.';
      },
    });
  }

  // Método para cerrar el modal de edición de prueba
  closeEditModal(): void {
    this.isEditModalOpen = false;
  }

  // Método para actualizar una prueba
  updatePrueba(idPrueba: number): void {
    if (
      !this.newPrueba.enunciado ||
      !this.newPrueba.puntuacionMaxima ||
      !this.newPrueba.idEspecialidad
    ) {
      this.error = 'Por favor, complete todos los campos.';
      return;
    }

    this.http
      .put(`http://localhost:8080/pruebas/${idPrueba}`, this.newPrueba)
      .subscribe({
        next: () => {
          this.loadPruebas(); // Recargar la lista de pruebas
          this.closeEditModal(); // Cerrar el modal
        },
        error: (err) => {
          console.error(err);
          this.error = 'Error al actualizar la prueba.';
        },
      });
  }

  // Método para eliminar una prueba
  deletePrueba(idPrueba: number): void {
    this.http.delete(`http://localhost:8080/pruebas/${idPrueba}`).subscribe({
      next: () => {
        this.loadPruebas(); // Recargar la lista de pruebas
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al eliminar la prueba.';
      },
    });
  }

  // Método para subir un PDF
  uploadPdf(pruebaId: number): void {
    if (this.selectedFile) {
      this.pruebasService.uploadPdf(pruebaId, this.selectedFile).subscribe({
        next: () => {
          this.closeModal();
          this.loadPruebas();
          this.resetForm();
        },
        error: (err) => {
          this.errorMessage = 'Error al subir el PDF: ' + (err.message || 'Error desconocido');
        }
      });
    } else {
      this.errorMessage = 'Por favor, selecciona un archivo PDF para subir.';
    }
  }

  // Método para ver un PDF
  viewPdf(ruta: string): void {
    this.pruebasService.viewPdf(ruta);
  }

  // Método para manejar la selección de archivos
  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  // Método para cerrar el modal
  closeModal(): void {
    // Implementa el cierre del modal
  }

  // Método para resetear el formulario
  resetForm(): void {
    this.selectedFile = null;
    this.errorMessage = '';
  }
}
