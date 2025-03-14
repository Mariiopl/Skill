import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  token: string;
  role: string; // admin, experto
  logeado: boolean;
  usuario: any; // objeto con los datos del usuario


  constructor(private http: HttpClient) {
    this.token = "";
    this.role = "";
    this.logeado = false;
    this.usuario = {};
  }

 private almacenar() {
    var objeto: any;
    objeto = {
      token: this.token,
      role: this.role,
      logeado: this.logeado,
      user: this.usuario
    }
    sessionStorage.setItem("LOGIN", JSON.stringify(objeto));
  }

  recuperar() {

    var cadena: string;
    cadena = sessionStorage.getItem("LOGIN")||""; 
    if (cadena != "") {

      var objeto = JSON.parse(cadena);

      this.token = objeto.token;
      this.role = objeto.role;
      this.logeado = objeto.logeado;
      this.usuario = objeto.usuario;

    } else {

      this.token = "";
      this.role = "";
      this.logeado = false;
      this.usuario = {};

    }

  }

  login(user:string, pass:string):Observable<any>{

    let objeto:any;
    objeto = this;

    return this.http.post("http://localhost:8080/auth/login", {
      username: user,
      password: pass
    })
    .pipe(map((data:any)=>{
      //Analizar respuesta
      let respuesta:object={};
      if(data!=null && data.token!=""){


        objeto.usuario={"nombre":data.username}
        objeto.role = data.role;
        objeto.token = data.token;
        objeto.logeado = true;
        objeto.almacenar();

        respuesta= {"funciona":true, "Role":data.role};

      }else{
        
        respuesta= {"funciona":false};
      }

      return respuesta;
      
    }))



  }

  private machacar() {
    sessionStorage.removeItem("LOGIN");
  }

  logout() {
    let cont: string | null = sessionStorage.getItem("LOGIN");
  
    if (cont) {
      let token = JSON.parse(cont).token;
  
      // Configurar los headers con el token
      let headers = { 'Authorization': `Bearer ${token}` };
  
      // Hacer el logout en el backend
      this.http.post("http://localhost:8080/auth/logout", {}, { headers })
        .subscribe({
          next: () => {
            // Eliminar sesión solo si el logout fue exitoso
            sessionStorage.removeItem("LOGIN");
          },
          error: (error) => {
            alert("No se pudo cerrar la sesión.");
          }
        });
    } else {
      console.warn("No hay sesión activa para cerrar.");
    }
  }

  isLogged():boolean {
    let respuesta:boolean=false;
    let cont:string|null=sessionStorage.getItem("LOGIN");
  
    if (cont) {
      respuesta=JSON.parse(cont||"").logeado;

    }
    return respuesta;
  }
  getNombre():string{
    let respuesta:string="";
    let cont:string|null=sessionStorage.getItem("LOGIN");
    if (cont) {
      respuesta=JSON.parse(cont||"").user.nombre;
    }
    return respuesta;
  }
  getPerfil():string{
    let respuesta:string="";
    let cont:string|null=sessionStorage.getItem("LOGIN");
    if (cont) {
      respuesta=JSON.parse(cont||"").role;
    }
    return respuesta;
  }
}
