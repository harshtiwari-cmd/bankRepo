package com.example.card.services;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;

import java.util.List;

public interface AtmService {
    AtmResponseDto registerAtm(AtmRequestDto requestDto);
    List<AtmResponseDto> getAtm();
}
