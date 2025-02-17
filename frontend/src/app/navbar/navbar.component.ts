import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-navbar',
  imports: [ CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  // logeado : boolean;
  perfil : string;
  nombre : string;
  // s:LoginService;

  constructor(private route:Router, private servicio: LoginService) {
    this.perfil = "";
    this.nombre = "";
  }

  logout() {
    this.servicio.logout();
    this.nombre = "";
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
}
