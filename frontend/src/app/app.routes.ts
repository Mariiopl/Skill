import { Routes } from '@angular/router';
import { ListaCompetidoresComponent } from './lista-competidores/lista-competidores.component';
// import { ListaParticipantesComponent } from './lista-participantes/lista-participantes.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { guardAdminGuard } from './autorizacion/guard-admin.guard';
import { guardExpertoGuard } from './autorizacion/guard-experto.guard';

export const routes: Routes = [
    { path: '', component: ListaCompetidoresComponent, pathMatch: 'full' },
    { path: 'login', component: LoginComponent, pathMatch: 'full' },
    { path: 'experto', component: ListaCompetidoresComponent, canActivate: [guardExpertoGuard], pathMatch: 'full' },
    { path: 'admin', component: AdminComponent, canActivate: [guardAdminGuard], pathMatch: 'full' },
    // Nueva ruta para el listado de participantes
    // { path: 'participantes', component: ListaParticipantesComponent, pathMatch: 'full' },
];


// import { Routes } from '@angular/router';
// import { ListaCompetidoresComponent } from './lista-competidores/lista-competidores.component';
// import { ListaParticipantesComponent } from './lista-participantes/lista-participantes.component';
// import { LoginComponent } from './login/login.component';
// import { AdminComponent } from './admin/admin.component';
// import { EspecialidadComponent } from './especialidad/especialidad.component';
// import { guardAdminGuard } from './autorizacion/guard-admin.guard';
// import { guardExpertoGuard } from './autorizacion/guard-experto.guard';

// export const routes: Routes = [
//   { path: '', component: ListaCompetidoresComponent, pathMatch: 'full' },
//   { path: 'login', component: LoginComponent, pathMatch: 'full' },
  
//   // Ruta de Experto con su ruta hija 'participantes'
//   {
//     path: 'experto', 
//     component: ListaCompetidoresComponent, 
//     canActivate: [guardExpertoGuard],
//     children: [
      
//       // Puedes añadir más rutas hijas aquí si es necesario
//     ]
//   },
  
//   // Ruta de Admin con su ruta hija 'especialidades'
//   {
//     path: 'admin', 
//     component: AdminComponent, 
//     canActivate: [guardAdminGuard],
//     children: [
//       { path: 'especialidades', component: EspecialidadComponent },
//       { path: 'participantes', component: ListaParticipantesComponent },
//       // Puedes añadir más rutas hijas aquí si es necesario
//     ]
//   },

// ];

