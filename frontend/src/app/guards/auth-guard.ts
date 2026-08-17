import { inject } from "@angular/core";
import { CanActivate, CanActivateFn, Router } from "@angular/router";
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