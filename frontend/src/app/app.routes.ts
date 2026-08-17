import { Routes } from '@angular/router';
import { Zones } from './pages/zones/zones';
import { Login } from './pages/login/login';
import { MesReservations } from './pages/mes-reservations/mes-reservations';
import { Historique } from './pages/historique/historique';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
    {path:'zones', component: Zones, canActivate: [authGuard]},
    {path:'login', component: Login, canActivate: [authGuard]},
    {path:'mes-reservations', component: MesReservations, canActivate: [authGuard]},
    {path:'historique', component: Historique, canActivate:[authGuard]},
];
