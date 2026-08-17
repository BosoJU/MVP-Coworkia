import { Routes } from '@angular/router';
import { Zones } from './pages/zones/zones';
import { Login } from './pages/login/login';
import { MesReservations } from './pages/mes-reservations/mes-reservations';
import { Historique } from './pages/historique/historique';

export const routes: Routes = [
    {path:'zones', component: Zones},
    {path:'login', component: Login},
    {path:'mes-reservations', component: MesReservations},
    {path:'historique', component: Historique},
];
