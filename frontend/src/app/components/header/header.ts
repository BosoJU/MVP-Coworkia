import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  menuOuvert = false;

  constructor(private auth: Auth, private router: Router){}

  ouvrirMenu():void{
    this.menuOuvert = !this.menuOuvert;
  }

  estConnecte(){
    return this.auth.estConnecte();
  }

  estAdmin(){
    return this.auth.estAdmin();
  }

  logout(){
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
