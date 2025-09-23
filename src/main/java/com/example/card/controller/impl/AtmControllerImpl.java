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
@RequestMapping("api/atms")
public class AtmControllerImpl implements AtmController {

    private final AtmService iAtmService;

    public AtmControllerImpl(AtmService iAtmService) {
        this.iAtmService = iAtmService;
    }

    @PostMapping
    public ResponseEntity<AtmResponseDto> registerAtm(@RequestBody @Valid AtmRequestDto atmRequestDto) throws ResourceNotFoundException {
        log.info("Received request to register ATM with ID: {}", atmRequestDto.getAtmId());
        try {
            AtmResponseDto response = iAtmService.registerAtm(atmRequestDto);
            log.info("ATM registered successfully with ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error occurred while registering ATM with ID: {}. Error: {}", atmRequestDto.getAtmId(), e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<AtmResponseDto>> getAllBranches() {
        log.info("Received request to fetch all ATMs");
        try {
            List<AtmResponseDto> atms = iAtmService.getAtm();

            if (atms.isEmpty()) {
                log.warn("No ATMs found in the system");
                return ResponseEntity.noContent().build();
            }

            log.info("Successfully fetched {} ATMs", atms.size());
            return ResponseEntity.ok(atms);
        } catch (Exception e) {
            log.error("Error occurred while fetching ATMs: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
}
