package com.example.card.adapter.api.services;

import com.example.card.domain.dto.AtmRequestDto;
import com.example.card.domain.dto.AtmResponseDto;

import java.util.List;

public interface AtmService {
    AtmResponseDto registerAtm(AtmRequestDto requestDto);
    List<AtmResponseDto> getAtm();
}
