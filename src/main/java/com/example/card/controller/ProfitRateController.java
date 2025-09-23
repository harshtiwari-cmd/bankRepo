package com.example.card.controller;

import com.example.card.constrants.dto.ProfitRateRequestDTO;
import com.example.card.constrants.dto.ProfitRateResponseDTO;
import com.example.card.services.ProfitRateService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/profit-rates")
public class ProfitRateController {

    private final ProfitRateService profitRateService;

    public ProfitRateController(ProfitRateService profitRateService) {
        this.profitRateService = profitRateService;
    }

    @GetMapping
    public ResponseEntity<List<ProfitRateResponseDTO>> getAllProfitRates() {
        log.info("Received Request to get all profit Rates");
        try {
            log.info("Successfully Fetched all profit Rates");
            return ResponseEntity.ok(profitRateService.getAllProfitRates());
        }
        catch (Exception e) {
            log.error("Exception occurred while fetching profit rates: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ProfitRateResponseDTO> createProfitRate(@RequestBody @Valid ProfitRateRequestDTO requestDTO)
    {
        log.info("Received request to create profit rate: {}", requestDTO);

        try {
            ProfitRateResponseDTO responseDTO = profitRateService.createProfitRate(requestDTO);
            log.info("Successfully fetched profit rate: {}", responseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
        catch (Exception e) {
            log.error("Exception occurred while creating profit rate", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
