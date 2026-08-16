package com.coworkia.backend.controllers;

import com.coworkia.backend.service.ZoneService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/private/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService){
        this.zoneService = zoneService;
    }

    @GetMapping
    public List<ZoneStatusResponse> getZones(){
        return zoneService.getZones();
    }

    @GetMapping("/{id}")
    public ZoneStatusResponse getZone (@PathVariable Long id){
        return zoneService.getZone(id);
    }


    @GetMapping("/{id}/disponibilite")
    public boolean estDisponible(@PathVariable Long id, @RequestParam LocalDateTime dateDebut, @RequestParam LocalDateTime dateFin) {
        return zoneService.estDisponible(id, dateDebut, dateFin);
    }
}
