package com.coworkia.backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Zone zone;

    @ManyToOne
    private InternalUser user;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    @Enumerated(EnumType.STRING)
    private ReservationStatus statut;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public InternalUser getUser() {
        return user;
    }

    public void setUser(InternalUser user) {
        this.user = user;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public ReservationStatus getStatut() {
        return statut;
    }

    public void setStatut(ReservationStatus statut) {
        this.statut = statut;
    }
}
