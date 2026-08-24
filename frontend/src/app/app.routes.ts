import { Routes } from '@angular/router';
import { Zones } from './pages/zones/zones';
import { Login } from './pages/login/login';
import { MesReservations } from './pages/mes-reservations/mes-reservations';
import { Historique } from './pages/historique/historique';
import { adminGuard, authGuard, loginGuard } from './guards/auth-guard';

export const routes: Routes = [
    {path:'', redirectTo:'login', pathMatch:'full'},
    {path:'zones', component: Zones, canActivate: [authGuard]},
    {path:'login', component: Login, canActivate: [loginGuard]},
    {path:'mes-reservations', component: MesReservations, canActivate: [authGuard]},
    {path:'historique', component: Historique, canActivate:[authGuard, adminGuard]},
    {path:'**', redirectTo:'login', pathMatch:'full'}
];
