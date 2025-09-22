package com.example.card.services;

import com.example.card.constrants.dto.FXRateDto;
import com.example.card.constrants.dto.FXRateResponseDto;
import com.example.card.constrants.dto.KioskResponseDTO;

import java.util.List;


public interface FXRateService {

    FXRateDto createFxRate(FXRateDto dto);
    List<FXRateResponseDto> getFx();
}
