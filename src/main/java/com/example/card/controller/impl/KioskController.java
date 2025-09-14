package com.example.card.controller.impl;

import com.example.card.dto.KioskRequestDTO;
import com.example.card.dto.KioskResponseDTO;
import com.example.card.services.KioskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}