package com.example.card.services;



import com.example.card.constrants.dto.KioskRequestDTO;
import com.example.card.constrants.dto.KioskResponseDTO;

public interface KioskService  {
     KioskResponseDTO createKiosk(KioskRequestDTO kioskDto);

}
