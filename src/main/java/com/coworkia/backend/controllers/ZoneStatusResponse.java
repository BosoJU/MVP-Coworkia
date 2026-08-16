package com.coworkia.backend.controllers;

public record ZoneStatusResponse(Long id, String code, String nom, int capacite, boolean occupee) {
}
