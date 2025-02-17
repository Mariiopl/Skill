import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { LoginService } from '../login.service';
import { Router } from '@angular/router';

export const guardExpertoGuard: CanActivateFn = (route, state) => {
    let respuesta:boolean=false;
    let service=inject(LoginService);
    let router=inject(Router);
    if(service.getPerfil()=="experto"){
      respuesta=true;
    }else{
      router.navigate(['/login']);
    }
    return respuesta;
};
