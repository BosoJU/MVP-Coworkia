package com.coworkia.backend.controllers;

import com.coworkia.backend.entities.Reservation;
import com.coworkia.backend.entities.ReservationStatus;
import com.coworkia.backend.entities.Zone;
import com.coworkia.backend.repository.ReservationRepository;
import com.coworkia.backend.repository.ZoneRepository;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/private/zones")
public class ZoneController {

    private final ZoneRepository zoneRepository;
    private final ReservationRepository reservationRepository;


    public ZoneController(ZoneRepository zoneRepository, ReservationRepository reservationRepository){
        this.zoneRepository = zoneRepository;
        this.reservationRepository = reservationRepository;
    }


    @GetMapping
    public List<ZoneStatusResponse> getZones(){
        LocalDateTime now = LocalDateTime.now();
        return zoneRepository.findAll().stream()
                .map(zone -> toStatusResponse(zone,now))
                .toList();
    }

    @GetMapping("/{id}")
    public ZoneStatusResponse getZone (@PathVariable Long id){
        Optional<Zone> zoneById = zoneRepository.findById(id);

        if(zoneById.isEmpty()){
            throw new NoSuchElementException("Zone introuvable " + id );
        }

        Zone zone = zoneById.get();
        LocalDateTime now = LocalDateTime.now();

        return toStatusResponse(zone, now);
    }

    private ZoneStatusResponse toStatusResponse (Zone zone, LocalDateTime now){
        List<Reservation> reservations = reservationRepository.findByZoneIdAndStatut(zone.getId(), ReservationStatus.CONFIRMEE);
        int nombreReservationsEnCours = 0;

        for (Reservation reservation : reservations) {
            if (reservation.getDateDebut().isBefore(now) && reservation.getDateFin().isAfter(now)) {
                nombreReservationsEnCours = nombreReservationsEnCours + 1;
            }
        }

        boolean occupee = nombreReservationsEnCours >= zone.getCapacite();
        Long id = zone.getId();
        String code = zone.getCode();
        String nom = zone.getNom();
        int capacite = zone.getCapacite();

        return new ZoneStatusResponse(id, code, nom ,capacite, occupee);
    }

    @GetMapping("/{id}/disponibilite")
    public boolean estDisponible(@PathVariable Long id, @RequestParam String dateDebut, @RequestParam String dateFin) {
        LocalDateTime debut = LocalDateTime.parse(dateDebut);
        LocalDateTime fin = LocalDateTime.parse(dateFin);

        Optional<Zone> optionalZone = zoneRepository.findById(id);

        if (optionalZone.isEmpty()) {
            throw new NoSuchElementException("Zone introuvable " + id);
        }

        Zone zone = optionalZone.get();

        List<Reservation> reservations = reservationRepository.findByZoneIdAndStatut(id, ReservationStatus.CONFIRMEE);
        int nombreReservationsSurCreneau = 0;

        for (Reservation reservation : reservations) {
            if (debut.isBefore(reservation.getDateFin()) && fin.isAfter(reservation.getDateDebut())) {
                nombreReservationsSurCreneau = nombreReservationsSurCreneau + 1;
            }
        }

        return nombreReservationsSurCreneau < zone.getCapacite();
    }



}
