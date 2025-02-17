import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  token: string;
  perfil: string; // admin, experto
  logeado: boolean;
  usuario: any; // objeto con los datos del usuario


  constructor(private http: HttpClient) {
    this.token = "";
    this.perfil = "";
    this.logeado = false;
    this.usuario = {};
  }

 private almacenar() {
    var objeto: any;
    objeto = {
      token: this.token,
      perfil: this.perfil,
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
      this.perfil = objeto.perfil;
      this.logeado = objeto.logeado;
      this.usuario = objeto.usuario;

    } else {

      this.token = "";
      this.perfil = "";
      this.logeado = false;
      this.usuario = {};

    }

  }

  login(user:string, pass:string):Observable<any>{

    let objeto:any;
    objeto = this;

    return this.http.post("http://localhost/SILVERIO/2-Trimestre/Servidor/login.php", {
      user: user,
      pass: pass
    })
    .pipe(map((data:any)=>{
      //Analizar respuesta
      let respuesta:object={};
      if(data!=null && data.token!=""){


        objeto.usuario={"nombre":data.user}
        objeto.perfil = data.perfil;
        objeto.token = data.token;
        objeto.logeado = true;
        objeto.almacenar();

        respuesta= {"funciona":true, "perfil":data.perfil};

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
    let objeto: any=this;
    let cont:string|null=sessionStorage.getItem("LOGIN");
    this.http.get("http://localhost/SILVERIO/2-Trimestre/Servidor/login.php?deslogear="+
                  JSON.parse(cont||"").token)
                  .subscribe(function(data) {
                    objeto.machacar();
                  })  
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
      respuesta=JSON.parse(cont||"").perfil;
    }
    return respuesta;
  }
}
