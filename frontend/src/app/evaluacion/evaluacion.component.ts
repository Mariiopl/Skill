import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule, NgIf, NgForOf } from '@angular/common';
import { FormsModule } from '@angular/forms'; 

@Component({
  selector: 'app-evaluacion',
  imports: [CommonModule, NgIf, NgForOf, FormsModule],
  templateUrl: './evaluacion.component.html',
  styleUrls: ['./evaluacion.component.css']
})
export class EvaluacionComponent implements OnInit {
  evaluaciones: any[] = []; // Guardar todas las evaluaciones
  evaluacionSeleccionada: any; // Guardar la evaluación seleccionada para el modal
  items: any[] = [];
  valoraciones: { [key: number]: number } = {};
  notaFinal: number = 0;
  modalAbierto: boolean = false;  // Controlar si el modal está abierto o cerrado

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadEvaluaciones(); // Cargar todas las evaluaciones
  }

  // Método para cargar todas las evaluaciones
  loadEvaluaciones(): void {
    this.http.get<any[]>('http://localhost:8080/evaluaciones').subscribe(data => {
      console.log('Evaluaciones recibidas:', data);
      this.evaluaciones = data; // Guardar las evaluaciones en el array
    }, error => {
      console.error('Error al cargar las evaluaciones:', error);
    });
  }

  // Método para abrir el modal y cargar la evaluación seleccionada
  openEvaluacionModal(evaluacion: any): void {
    this.evaluacionSeleccionada = evaluacion;
    this.items = evaluacion.prueba.items;  // Cargar los items de la prueba
    this.valoraciones = {};  // Inicializar el objeto de valoraciones
    this.notaFinal = 0;  // Inicializar la nota final
    this.modalAbierto = true;  // Abrir el modal
  }

  // Método para cerrar el modal
  closeModal(): void {
    this.modalAbierto = false;  // Cerrar el modal
  }

  calcularNotaFinal(): void {
    let total = 0;
    let maxTotal = 0;
  
    // Calcular el puntaje total ponderado
    this.items.forEach(item => {
      const valoracion = this.valoraciones[item.idItem] || 0;
      total += (valoracion / item.gradosConsecuion) * item.peso;  // Valor ponderado
      maxTotal += item.peso;  // Sumar el peso de todos los ítems
    });
  
    // Calcular la nota final proporcional y asegurarse que no supere el máximo de 10
    this.notaFinal = maxTotal > 0 ? (total / maxTotal) * 10 : 0;
  
    // Limitar la nota final a un máximo de 10
    if (this.notaFinal > 10) {
      this.notaFinal = 10;
    }
  }
  
  guardarEvaluacion(): void {
    const evaluacionItems = this.items.map(item => ({
      item: { idItem: item.idItem },
      valoracion: this.valoraciones[item.idItem] || 0
    }));

    const evaluacionData = {
      idEvaluacion: this.evaluacionSeleccionada.idEvaluacion,
      notaFinal: this.notaFinal,
      evaluacionItems: evaluacionItems
    };

    this.http.put(`http://localhost:8080/evaluaciones/${this.evaluacionSeleccionada.idEvaluacion}`, evaluacionData)
      .subscribe(response => {
        console.log('Evaluación guardada con éxito', response);
        this.closeModal();  // Cerrar modal después de guardar
      }, error => {
        console.error('Error al guardar la evaluación:', error);
      });
  }
}
