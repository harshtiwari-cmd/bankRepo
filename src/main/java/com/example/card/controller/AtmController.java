package com.example.card.controller;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import org.springframework.http.ResponseEntity;

public interface AtmController {
    ResponseEntity<AtmResponseDto> registerAtm(AtmRequestDto atmRequestDto);
}
