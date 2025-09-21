package com.example.card.services;

import com.example.card.constrants.dto.KioskRequestDTO;
import com.example.card.constrants.dto.KioskResponseDTO;

import java.util.List;

public interface KioskService  {
     KioskResponseDTO createKiosk(KioskRequestDTO kioskDto);
     List<KioskResponseDTO> getKiosk();
}
