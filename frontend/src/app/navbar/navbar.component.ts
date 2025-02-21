import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [ CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  // logeado : boolean;
  role : string;
  nombre : string;
  // s:LoginService;

  constructor(private route:Router, private servicio: LoginService) {
    this.role = "";
    this.nombre = "";
  }

  logout() {
    this.servicio.logout();
    this.route.navigate(['/']);
  }
  login() {
    this.route.navigate(['/login']);
  }
  isLogged() {
    return this.servicio.isLogged();
  }
  getNombre():string {
    return this.servicio.getNombre();
  }
  getRole(): string {
    return this.servicio.getPerfil();
  }
}
