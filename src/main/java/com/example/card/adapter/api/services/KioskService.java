package com.example.card.adapter.api.services;

import com.example.card.domain.dto.KioskRequestDTO;
import com.example.card.domain.dto.KioskResponseDTO;

import java.util.List;

public interface KioskService {
    KioskResponseDTO createKiosk(KioskRequestDTO kioskDto);

    List<KioskResponseDTO> getKiosk();
}
