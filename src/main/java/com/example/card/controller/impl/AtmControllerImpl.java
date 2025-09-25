package com.example.card.controller.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.controller.AtmController;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.AtmService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/atms")
public class AtmControllerImpl implements AtmController {

    private final AtmService atmService;

    public AtmControllerImpl(AtmService atmService) {
        this.atmService = atmService;
    }

    @PostMapping
    public ResponseEntity<AtmResponseDto> registerAtm(@RequestBody @Valid AtmRequestDto atmRequestDto) {
        log.info("Received request to register ATM location with code: {}", atmRequestDto.getCode());
        AtmResponseDto response = atmService.registerAtm(atmRequestDto);
        log.info("ATM location registered successfully with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AtmResponseDto>> getAllAtms() {
        log.info("Received request to fetch all ATM locations");
        List<AtmResponseDto> atms = atmService.getAtm();

        if (atms.isEmpty()) {
            log.warn("No ATM locations found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} ATM locations", atms.size());
        return ResponseEntity.ok(atms);
    }
}
