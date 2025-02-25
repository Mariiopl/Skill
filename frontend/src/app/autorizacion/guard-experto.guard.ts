import { ActivatedRoute, ActivatedRouteSnapshot, CanActivate, GuardResult, RouterStateSnapshot } from '@angular/router';
import { LoginService } from '../login.service';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

// export const guardExpertoGuard: CanActivateFn = (route, state) => {
//     let respuesta:boolean=false;
//     let service=inject(LoginService);
//     let router=inject(Router);
//     if(service.getPerfil()=="experto"){
//       respuesta=true;
//     }else{
//       router.navigate(['/login']);
//     }
//     return respuesta;
// };
@Injectable({
  providedIn: 'root'
})
export class guardExpertoGuard implements CanActivate {
  constructor(private service: LoginService, private router: Router) {}
  canActivate(
    route:ActivatedRouteSnapshot, 
    state:RouterStateSnapshot): boolean {
      var respuesta:boolean = false
      if(this.service.getPerfil()=="experto"){
        respuesta=true;
      }else{
        this.router.navigate(['/login']);
      }
      return respuesta;
  }
}