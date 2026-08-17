import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private urlLogin = 'htpp://localhost:8080/api/private/login';

  constructor(private http: HttpClient){}

  login (email: string, password: string): Observable<string>{
    const body = { email: email, password: password};
    return this.http.post(this.urlLogin, body, { responseType: 'text'});
  }

  logout():void{
    localStorage.removeItem('token');
  }

  getToken(): string | null{
    return localStorage.getItem('token');
  }

  estConnecte(): boolean{
    return this.getToken() !== null;
  }

  estAdmin():boolean{
    const token = this.getToken();
    if(token === null){
      return false
    }
    const payload = token.split('.')[1];
    const decoded = JSON.parse(atob(payload));
    return decoded.scope.incules('ROLE_ADMIN');
  }
}
