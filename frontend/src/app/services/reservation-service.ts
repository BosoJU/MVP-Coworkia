import { HttpBackend, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Auth } from './auth';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  private urlReservation = "http://localhost:8080/api/private/reservations";

  constructor(private http: HttpClient, private authService : Auth){}

  creerReservation(zoneId: number, dateDebut: string, dateFin: string): Observable<any>{
    const headers = { Authorization: 'Bearer ' + this.authService.getToken()};

    const body = {
      zone : {id : zoneId},
      dateDebut: dateDebut,
      dateFin: dateFin
    }

    return this.http.post(this.urlReservation, body , {headers: headers});
  }

  getMesReservation(id: number): Observable<any> {
    const headers = { Authorization: 'Bearer ' + this.authService.getToken()};

    return this.http.get<Reservation[]>(this.urlReservation + '/me', {headers:headers});
  }

  annulerReservation(id: number): Observable<any>{
    const headers = { Authorization: 'Bearer ' + this.authService.getToken()};

    return this.http.delete(this.urlReservation+ '/' + id, {headers:headers});
  }

  getHistorique():Observable<Reservation[]>{
    const headers = { Authorization: 'Bearer ' + this.authService.getToken()};

    return this.http.get<Reservation[]>(this.urlReservation + '/historique', {headers:headers});
  }

  getFutures(): Observable<Reservation[]> {
    const headers = { Authorization: 'Bearer ' + this.authService.getToken()};

    return this.http.get<Reservation[]>(this.urlReservation + '/futures', {headers:headers});
  }

}
