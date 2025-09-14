package com.example.card.controller;

import com.example.card.dto.KioskRequestDTO;
import com.example.card.dto.KioskResponseDTO;
import org.springframework.http.ResponseEntity;

public interface KioskController {

    ResponseEntity<KioskResponseDTO> createKiosk(KioskRequestDTO kiosk);
}
