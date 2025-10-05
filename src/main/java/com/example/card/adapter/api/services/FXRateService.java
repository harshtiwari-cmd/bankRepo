package com.example.card.adapter.api.services;

import com.example.card.domain.dto.FXRateDto;
import com.example.card.domain.dto.FXRateResponseDto;

import java.util.List;


public interface FXRateService {

    FXRateDto createFxRate(FXRateDto dto);

    List<FXRateResponseDto> getFx();
}
