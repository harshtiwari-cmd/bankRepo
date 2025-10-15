package com.example.card.adapter.api.controller;

import com.example.card.domain.dto.ProfitRateRequestDTO;
import com.example.card.domain.dto.ProfitRateResponseDTO;
import com.example.card.adapter.api.services.ProfitRateService;
import com.example.card.infrastructure.common.AppConstant;
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
    public ResponseEntity<List<ProfitRateResponseDTO>> getAllProfitRates(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId
    ) {
        log.info("GET /api/profit-rates - Received Request to get all profit Rates");
        try {
            log.info("Successfully Fetched all profit Rates");
            return ResponseEntity.ok(profitRateService.getAllProfitRates());
        } catch (Exception e) {
            log.error("Exception occurred while fetching profit rates: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ProfitRateResponseDTO> createProfitRate(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @RequestBody @Valid ProfitRateRequestDTO requestDTO) {
        log.info("POST /api/profit-rates - Received request to create profit rate: {}", requestDTO);

        try {
            ProfitRateResponseDTO responseDTO = profitRateService.createProfitRate(requestDTO);
            log.info("Successfully fetched profit rate: {}", responseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            log.error("Exception occurred while creating profit rate", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
