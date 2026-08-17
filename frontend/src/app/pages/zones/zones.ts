import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Zone } from '../../models/zone';
import { ZoneService } from '../../services/zone-service';
import { ReservationService } from '../../services/reservation-service';
import { FormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-zones',
  imports: [FormsModule, NgClass],
  templateUrl: './zones.html',
  styleUrl: './zones.css',
})
export class Zones implements OnInit{
  zones: Zone[] = [];
  zoneSelectionnee: Zone | null = null;
  dateDebut='';
  dateFin= '';
  creneauDisponible: boolean | null = null;
  erreurDates='';

  messageReservation='';
  reservationReussie = false;

  constructor(private zoneService: ZoneService, private reservationService : ReservationService, private changeDetector: ChangeDetectorRef){}

  ngOnInit(): void {
    this.zoneService.getZones().subscribe({
      next: (data) =>  {
        this.zones = data;
        this.changeDetector.detectChanges() ;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  ouvrirFormulaireDate(zone:Zone){
    this.zoneSelectionnee = zone;
    this.messageReservation = '';
    this.creneauDisponible = null;
    this.erreurDates = '';
    this.dateDebut= '';
    this.dateFin= '';
  }

  reserver(){
    if(this.zoneSelectionnee === null){
      return;
    }

    this.reservationService.creerReservation(this.zoneSelectionnee.id, this.dateDebut, this.dateFin).subscribe({
      next: ()=> {
        this.messageReservation = 'Réservation confirmée';
        this.reservationReussie = true;
        this.zoneSelectionnee = null;
        
        this.zoneService.getZones().subscribe({
          next:(data) => {
          this.zones = data;
          //ajout changeDetector sinon les messages de confirmations sont décalés car seulement pris en compte au prochain changement 
          this.changeDetector.detectChanges();
          }
        });
      },
      error: (err) => {
        this.messageReservation = 'Erreur lors de la réservation';
        this.reservationReussie = false;
        this.changeDetector.detectChanges();
      }
    });
  }

  verifierDisponibilite(){
    if(this.zoneSelectionnee === null || this.dateDebut === '' || this. dateFin === ''){
      return;
    }

    this.zoneService.estDisponible(this.zoneSelectionnee.id, this.dateDebut, this.dateFin).subscribe({
      next:(disponible) => {
        this.creneauDisponible = disponible; 
        this.changeDetector.detectChanges();
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

}
