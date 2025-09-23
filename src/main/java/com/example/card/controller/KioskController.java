package com.example.card.controller;

import com.example.card.constrants.dto.KioskRequestDTO;
import com.example.card.constrants.dto.KioskResponseDTO;
import com.example.card.services.KioskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/kiosk")
public class KioskController {

    private final KioskService service;

    public KioskController(KioskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<KioskResponseDTO> createKiosk(@RequestBody @Valid KioskRequestDTO kiosk) {

        log.info("POST /kiosk - Received request to create Kiosk: {}", kiosk);

        try {
            KioskResponseDTO kiosk1 = service.createKiosk(kiosk);
            log.info("Kiosk created Successfully with id: {}", kiosk1.getKioskId());
            return ResponseEntity.status(HttpStatus.CREATED).body(kiosk1);
        }
        catch (Exception e) {
            log.error("Exception while creating Kiosk: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public ResponseEntity<List<KioskResponseDTO>> getKiosk() {

        log.info("GET /kiosk - Received request to fetch all Kiosk");

        try {
            List<KioskResponseDTO> kiosks = service.getKiosk();

            if (kiosks.isEmpty()) {
                log.warn("No Kiosk records found");
                return ResponseEntity.noContent().build();
            }

            log.info("Fetched {} kiosk records", kiosks.size());
            return ResponseEntity.ok(kiosks);
        }
        catch (Exception e) {
            log.error("Exception while fetching Kiosk: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
}