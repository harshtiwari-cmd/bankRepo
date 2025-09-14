package com.example.card.services;

import com.example.card.dto.KioskRequestDTO;
import com.example.card.dto.KioskResponseDTO;

public interface KioskService  {
     KioskResponseDTO createKiosk(KioskRequestDTO kioskDto);
}
