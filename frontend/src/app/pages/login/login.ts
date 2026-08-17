import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  email ='';
  password= '';
  messageErreur='';

  constructor(private auth: Auth, private router: Router){}

  onSubmit(): void{
    this.auth.login(this.email, this.password).subscribe({
      next: (token) => {
        localStorage.setItem('token', token);
        this.router.navigate(['/zones']);
      }, 
      error: () => {
        this.messageErreur = 'Identifiants incorrects';
      }
    })
  }

}
