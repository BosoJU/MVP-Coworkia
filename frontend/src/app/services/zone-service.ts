import { Injectable } from '@angular/core';
import { Auth } from './auth';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Zone } from '../models/zone';

@Injectable({
  providedIn: 'root',
})
export class ZoneService {
  private urlZones = 'http//localhost:8080/api/private/zones';

  constructor(private http: HttpClient, private auth: Auth) {}

  getZones(): Observable<Zone[]>{
    const headers = { Authorization: 'Bearer ' + this.auth.getToken()};
    return this.http.get<Zone[]>(this.urlZones, {headers});
  }

  getZone(id: number):Observable<Zone>{
    const headers = { Authorization: 'Bearer ' + this.auth.getToken()};
    return this.http.get<Zone>(`${this.urlZones}/${id}`, { headers });
  } 

  estdisponible(id: number, dateDebut:string, dateFin: string): Observable<boolean>{
    const headers = { Authorization: 'Bearer ' + this.auth.getToken()};
    const params = new HttpParams()
    .set('dateDebut', dateDebut)
    .set('dateFin', dateFin);
    return this.http.get<boolean>(`${this.urlZones}/${id}/disponibilite`, { headers, params})
  }
}
