package com.coworkia.backend.controllers;

import com.coworkia.backend.entities.InternalUser;
import com.coworkia.backend.entities.Reservation;
import com.coworkia.backend.entities.ReservationStatus;
import com.coworkia.backend.entities.Zone;
import com.coworkia.backend.repository.InternalUserRepository;
import com.coworkia.backend.repository.ReservationRepository;
import com.coworkia.backend.repository.ZoneRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/private/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ZoneRepository zoneRepository;
    private final InternalUserRepository internalUserRepository;

    public ReservationController (ReservationRepository reservationRepository, ZoneRepository zoneRepository, InternalUserRepository internalUserRepository){
        this.internalUserRepository = internalUserRepository;
        this.reservationRepository = reservationRepository;
        this.zoneRepository = zoneRepository;
    }

    @PostMapping
    public Reservation createReservation (@RequestBody Reservation reservation, Authentication authentication){
        String email = authentication.getName();
        Optional<InternalUser> DoUserExist = internalUserRepository.findByEmail(email);

        if(DoUserExist.isEmpty()){
            throw new NoSuchElementException("Utilisateur introuvable " + email);
        }

        InternalUser utilisateur = DoUserExist.get();

        Long zoneId = reservation.getZone().getId();
        Optional<Zone> idZoneDeReservation = zoneRepository.findById(zoneId);

        if(idZoneDeReservation.isEmpty()){
            throw new NoSuchElementException("Zone introuvable" + zoneId);
        }

        Zone zone = idZoneDeReservation.get();

        //Vérificatioin de chevauchement pour le temps réel
        if(!reservation.getDateDebut().isBefore(reservation.getDateFin())){
            throw new IllegalArgumentException("La date de début doit être avant la date de fin");
        }

        List<Reservation> reservationsExistantes = reservationRepository.findByZoneIdAndStatut(zone.getId(), ReservationStatus.CONFIRMEE);

        int nombreReservationsSurCreneau = 0;

        for (Reservation reservationExistante : reservationsExistantes){
            if (reservation.getDateDebut().isBefore(reservationExistante.getDateFin())
                    && reservation.getDateFin().isAfter(reservationExistante.getDateDebut())) {
                nombreReservationsSurCreneau = nombreReservationsSurCreneau + 1;
            }
        }

        if (nombreReservationsSurCreneau >= zone.getCapacite()){
            throw new IllegalStateException("Ce créneau n'est pas disponible pour cette zone");
        }

        reservation.setZone(zone);
        reservation.setUser(utilisateur);
        reservation.setStatut(ReservationStatus.CONFIRMEE);

        return reservationRepository.save(reservation);
    }

    @DeleteMapping("/{id}")
    public void annulerReservation (@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        Reservation reservationASupprimer = optionalReservation.get();

        String email = authentication.getName();
        boolean proprietaireReservation = reservationASupprimer.getUser().getEmail().equals(email);

        boolean estAdmin = false;
        for (GrantedAuthority authority : authentication.getAuthorities()){
            if (authority.getAuthority().equals("SCOPE_ROLE_ADMIN")){
                estAdmin = true;
            }
        }

        if(!proprietaireReservation && !estAdmin){
            throw new AccessDeniedException("Vous ne pouvez annuler que vos réservations!");
        }

        reservationASupprimer.setStatut(ReservationStatus.ANNULEE);
        reservationRepository.save(reservationASupprimer);
    }

    @GetMapping("/historique")
    public List<Reservation> getHistorique(@RequestParam(required = false) Long zoneId){
        LocalDateTime now = LocalDateTime.now();

        if (zoneId == null){
            return reservationRepository.findByDateFinBefore(now);
        } else {
            return reservationRepository.findByZoneIdAndDateFinBefore(zoneId, now);
        }
    }

    @GetMapping("/futures")
    public List<Reservation> getReservationsFutures(@RequestParam(required = false) Long zoneId) {
        LocalDateTime now = LocalDateTime.now();

        if (zoneId == null) {
            return reservationRepository.findByDateFinAfter(now);
        } else {
            return reservationRepository.findByZoneIdAndDateFinAfter(zoneId, now);
        }
    }

    @GetMapping("/me")
    public List<Reservation> getMesReservations(Authentication authentication) {
        String email = authentication.getName();
        Optional<InternalUser> optionalUser = internalUserRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("Utilisateur introuvable " + email);
        }

        InternalUser utilisateur = optionalUser.get();

        return reservationRepository.findByUser(utilisateur);
    }
}
