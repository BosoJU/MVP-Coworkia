package com.coworkia.backend.controllers;

import com.coworkia.backend.entities.Reservation;
import com.coworkia.backend.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/private/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController (ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping
    public Reservation createReservation (@RequestBody Reservation reservation, Authentication authentication){
       return reservationService.createReservation(reservation,authentication);
    }

    @DeleteMapping("/{id}")
    public void annulerReservation (@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        reservationService.annulerReservation(id, authentication);
    }

    @GetMapping("/historique")
    public List<Reservation> getHistorique(@RequestParam(required = false) Long zoneId){
        return reservationService.getHistorique(zoneId);
    }

    @GetMapping("/futures")
    public List<Reservation> getReservationsFutures(@RequestParam(required = false) Long zoneId) {
       return reservationService.getReservationsFutures(zoneId);
    }

    @GetMapping("/me")
    public List<Reservation> getMesReservations(Authentication authentication) {
        return reservationService.getMesReservations(authentication);
    }
}
