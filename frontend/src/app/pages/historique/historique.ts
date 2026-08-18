import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Reservation } from '../../models/reservation';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-historique',
  imports: [CommonModule],
  templateUrl: './historique.html',
  styleUrl: './historique.css',
})
export class Historique implements OnInit{
  reservationsFutures: Reservation[] = [];
  reservationsPassees: Reservation[] = [];

  constructor(private reservationService: ReservationService, private changeDetector: ChangeDetectorRef){}

  ngOnInit(): void {
    this.reservationService.getFutures().subscribe({
      next: (data) =>  {
        this.reservationsFutures = data;
        this.changeDetector.detectChanges();
      },
      error: (err) => {
        console.error(err);
      }
    });

    this.reservationService.getHistorique().subscribe({
      next:(data) => {
        this.reservationsPassees = data;
        this.changeDetector.detectChanges();
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}
