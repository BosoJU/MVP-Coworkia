package com.coworkia.backend.service;

import com.coworkia.backend.entities.InternalUser;
import com.coworkia.backend.entities.Reservation;
import com.coworkia.backend.entities.ReservationStatus;
import com.coworkia.backend.entities.Zone;
import com.coworkia.backend.repository.InternalUserRepository;
import com.coworkia.backend.repository.ReservationRepository;
import com.coworkia.backend.repository.ZoneRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ZoneRepository zoneRepository;
    private final InternalUserRepository internalUserRepository;

    public ReservationService (ReservationRepository reservationRepository, ZoneRepository zoneRepository, InternalUserRepository internalUserRepository){
        this.internalUserRepository = internalUserRepository;
        this.reservationRepository = reservationRepository;
        this.zoneRepository = zoneRepository;
    }

    public Reservation createReservation (Reservation reservation, Authentication authentication){
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

        //verification de date passée
        if(reservation.getDateDebut().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Vous ne pouvez pas réserver dans le passé");
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

    public void annulerReservation (Long id, Authentication authentication) throws AccessDeniedException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isEmpty()){
            throw  new NoSuchElementException("Réservation introuvable" + id);
        }

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

    public List<Reservation> getHistorique(Long zoneId){
        LocalDateTime now = LocalDateTime.now();

        if (zoneId == null){
            return reservationRepository.findByDateFinBefore(now);
        } else {
            return reservationRepository.findByZoneIdAndDateFinBefore(zoneId, now);
        }
    }

    public List<Reservation> getReservationsFutures(Long zoneId) {
        LocalDateTime now = LocalDateTime.now();

        if (zoneId == null) {
            return reservationRepository.findByDateFinAfter(now);
        } else {
            return reservationRepository.findByZoneIdAndDateFinAfter(zoneId, now);
        }
    }

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
