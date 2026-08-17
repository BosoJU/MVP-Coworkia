package com.coworkia.backend.service;

import com.coworkia.backend.controllers.ZoneStatusResponse;
import com.coworkia.backend.entities.Reservation;
import com.coworkia.backend.entities.ReservationStatus;
import com.coworkia.backend.entities.Zone;
import com.coworkia.backend.repository.ReservationRepository;
import com.coworkia.backend.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final ReservationRepository reservationRepository;

    public ZoneService (ZoneRepository zoneRepository, ReservationRepository reservationRepository){
        this.zoneRepository = zoneRepository;
        this.reservationRepository = reservationRepository;
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

   public ZoneStatusResponse getZone(Long id){
        Optional<Zone> zoneById = zoneRepository.findById(id);
        if(zoneById.isEmpty()){
            throw new NoSuchElementException("Zone introuvable" + id);
        }
        Zone zone = zoneById.get();
        LocalDateTime now = LocalDateTime.now();
        return toStatusResponse(zone, now);
   }

    public boolean estDisponible(Long id, LocalDateTime dateDebut, LocalDateTime dateFin) {
        Optional<Zone> optionalZone = zoneRepository.findById(id);

        if (optionalZone.isEmpty()) {
            throw new NoSuchElementException("Zone introuvable " + id);
        }

        Zone zone = optionalZone.get();

        List<Reservation> reservations = reservationRepository.findByZoneIdAndStatut(id, ReservationStatus.CONFIRMEE);
        int nombreReservationsSurCreneau = 0;

        for (Reservation reservation : reservations) {
            if (dateDebut.isBefore(reservation.getDateFin()) && dateFin.isAfter(reservation.getDateDebut())) {
                nombreReservationsSurCreneau = nombreReservationsSurCreneau + 1;
            }
        }

        return nombreReservationsSurCreneau < zone.getCapacite();
    }

    public List<ZoneStatusResponse> getZones() {
        LocalDateTime now = LocalDateTime.now();
        return zoneRepository.findAll().stream()
                .map(zone-> toStatusResponse(zone,now))
                .toList();
    }
}
