import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Reservation } from '../../models/reservation';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-mes-reservations',
  imports: [CommonModule],
  templateUrl: './mes-reservations.html',
  styleUrl: './mes-reservations.css',
})
export class MesReservations implements OnInit{
  reservations: Reservation[] = [];
  message= '';

  constructor(private reservationService: ReservationService, private changeDetector: ChangeDetectorRef){}

  ngOnInit(): void {
    this.chargerReservation();
  }

  chargerReservation(){
    this.reservationService.getMesReservations().subscribe({
      next: (data) => {
        this.reservations = data;
        this.changeDetector.detectChanges();
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  annuler(id: number) {
    this.reservationService.annulerReservation(id).subscribe({
      next: () => {
        this.message = 'Réservation annulée';
        this.chargerReservation();
      },
      error: (err) => {
        this.message = 'Erreur lors de l\'annulation';
        this.changeDetector.detectChanges();
      }
    });
  }
}
