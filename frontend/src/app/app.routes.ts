// import { Routes } from '@angular/router';
// import { ListaCompetidoresComponent } from './lista-competidores/lista-competidores.component';
// // import { ListaParticipantesComponent } from './lista-participantes/lista-participantes.component';
// import { LoginComponent } from './login/login.component';
// import { AdminComponent } from './admin/admin.component';
// import { guardAdminGuard } from './autorizacion/guard-admin.guard';
// import { guardExpertoGuard } from './autorizacion/guard-experto.guard';

// export const routes: Routes = [
//     { path: '', component: ListaCompetidoresComponent, pathMatch: 'full' },
//     { path: 'login', component: LoginComponent, pathMatch: 'full' },
//     { path: 'experto', component: ListaCompetidoresComponent, canActivate: [guardExpertoGuard], pathMatch: 'full' },
//     { path: 'admin', component: AdminComponent, canActivate: [guardAdminGuard], pathMatch: 'full' },
//     // Nueva ruta para el listado de participantes
//     // { path: 'participantes', component: ListaParticipantesComponent, pathMatch: 'full' },
// ];


import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { guardAdminGuard } from './autorizacion/guard-admin.guard';
import { guardExpertoGuard } from './autorizacion/guard-experto.guard';
import { ExpertoComponent } from './experto/experto.component';
import { ExpertosComponent } from './expertos/expertos.component';
import { EspecialidadComponent } from './especialidad/especialidad.component';
import { ListaParticipantesComponent } from './lista-participantes/lista-participantes.component';

export const routes: Routes = [
    { path: '', component: ListaParticipantesComponent, pathMatch: 'full' },
    { path: 'login', component: LoginComponent, pathMatch: 'full' },
    { path: 'experto', component: ExpertoComponent,canActivate: [guardExpertoGuard], pathMatch: 'full',
        children: [
            { path: 'competidores', component: ListaParticipantesComponent, pathMatch: 'full' },
            { path: 'pruebas', component: ExpertoComponent, pathMatch: 'full' },
            { path: 'evaluaciones', component: ExpertoComponent, pathMatch: 'full' },
           
        ]   
     },
    { path: 'admin', canActivate: [guardAdminGuard],
        children: [
            { path: '', redirectTo: 'expertos', pathMatch: 'full' },
            { path: 'expertos', component:ExpertosComponent ,canActivate: [guardAdminGuard] },
            { path: 'ganadores', component: AdminComponent, canActivate: [guardAdminGuard],pathMatch: 'full' },
            { path: 'especialidad', component:EspecialidadComponent,canActivate: [guardAdminGuard], pathMatch: 'full' },
           
        ]
    },

];

