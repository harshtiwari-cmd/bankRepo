package com.example.card.services;

import com.example.card.dto.KioskRequestDTO;
import com.example.card.dto.KioskResponseDTO;

import java.util.List;

public interface KioskService  {
     KioskResponseDTO createKiosk(KioskRequestDTO kioskDto);
     List<KioskResponseDTO> getKiosk();
}
