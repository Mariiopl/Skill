import { ActivatedRoute, ActivatedRouteSnapshot, CanActivate, GuardResult, RouterStateSnapshot } from '@angular/router';
import { LoginService } from '../login.service';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
// export const guardianGuard: CanActivateFn = (route, state) => {
//   return true;
// };
@Injectable({
  providedIn: 'root'
})
export class guardAdminGuard implements CanActivate {
  constructor(private service: LoginService, private router: Router) {}
  canActivate(
    route:ActivatedRouteSnapshot, 
    state:RouterStateSnapshot): boolean {
      var respuesta:boolean = false
      if(this.service.getPerfil()=="admin"){
        respuesta=true;
      }else{
        this.router.navigate(['/login']);
      }
      return respuesta;
  }
}
