import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { Auth } from "../services/auth";

export const authGuard: CanActivateFn = () => {
    const auth = inject(Auth);
    const router = inject(Router);

    if(!auth.estConnecte()){
        router.navigateByUrl('/login');
        return false;
    }
    return true
}
//Sans ce guard, on accède à la page login en étant connecté 
export const loginGuard: CanActivateFn = () => {
    const auth = inject(Auth);
    const router = inject(Router);

    if(!auth.estConnecte()){
        return true
    }

    router.navigate(['/zones']);
    return false;
}
