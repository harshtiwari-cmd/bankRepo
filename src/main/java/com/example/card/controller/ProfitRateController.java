package com.example.card.controller;

import com.example.card.constrants.dto.ProfitRateRequestDTO;
import com.example.card.constrants.dto.ProfitRateResponseDTO;
import com.example.card.services.ProfitRateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profit-rates")
public class ProfitRateController {

    private final ProfitRateService profitRateService;

    public ProfitRateController(ProfitRateService profitRateService) {
        this.profitRateService = profitRateService;
    }

    @GetMapping
    public ResponseEntity<List<ProfitRateResponseDTO>> getAllProfitRates() {
        return ResponseEntity.ok(profitRateService.getAllProfitRates());
    }

    @PostMapping
    public ResponseEntity<ProfitRateResponseDTO> createProfitRate(@RequestBody @Valid ProfitRateRequestDTO requestDTO)
    {
        ProfitRateResponseDTO responseDTO = profitRateService.createProfitRate(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }
}
