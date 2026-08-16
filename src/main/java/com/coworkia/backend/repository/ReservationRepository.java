package com.coworkia.backend.repository;

import com.coworkia.backend.entities.InternalUser;
import com.coworkia.backend.entities.Reservation;
import com.coworkia.backend.entities.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByZoneIdAndStatut(Long zoneId, ReservationStatus statut);
    List<Reservation> findByDateFinBefore(LocalDateTime now);
    List<Reservation> findByZoneIdAndDateFinBefore(Long zoneId, LocalDateTime now);
    List<Reservation> findByDateFinAfter(LocalDateTime now);
    List<Reservation> findByZoneIdAndDateFinAfter(Long zoneId, LocalDateTime now);
    List<Reservation> findByUser(InternalUser user);
}
