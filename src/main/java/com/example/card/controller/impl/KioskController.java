package com.example.card.controller.impl;


import com.example.card.constrants.dto.KioskRequestDTO;
import com.example.card.constrants.dto.KioskResponseDTO;
import com.example.card.services.KioskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kiosk")
public class KioskController {

    private final KioskService service;

    public KioskController(KioskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<KioskResponseDTO> createKiosk(@RequestBody @Valid KioskRequestDTO kiosk) {
        KioskResponseDTO kiosk1 = service.createKiosk(kiosk);
        return ResponseEntity.status(HttpStatus.CREATED).body(kiosk1);
    }

    @GetMapping
    public ResponseEntity<List<KioskResponseDTO>> getKiosk() {
        try {
            List<KioskResponseDTO> kiosks = service.getKiosk();

            if (kiosks.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(kiosks);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}