package com.example.card.adapter.api.controller;

import com.example.card.domain.dto.AtmRequestDto;
import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.adapter.api.services.AtmService;
import com.example.card.infrastructure.common.AppConstant;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/atms")
public class AtmController {

    private final AtmService atmService;

    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    @PostMapping
    public ResponseEntity<AtmResponseDto> registerAtm(
            @RequestHeader(name = AppConstant.UNIT, required = false) String unit,
            @RequestHeader(name = AppConstant.CHANNEL, required = false) String channel,
            @RequestHeader(name = AppConstant.ACCEPT_LANGUAGE,required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = false) String serviceId,
            @RequestHeader(name = AppConstant.SCREENID,required = false) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = false) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = false) String subModuleId,
            @RequestBody @Valid AtmRequestDto atmRequestDto) {
        log.info("Received request to register ATM location with code: {}", atmRequestDto.getCode());
        AtmResponseDto response = atmService.registerAtm(atmRequestDto);
        log.info("ATM location registered successfully with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AtmResponseDto>> getAllAtms(
            @RequestHeader(name = AppConstant.UNIT, required = false) String unit,
            @RequestHeader(name = AppConstant.CHANNEL, required = false) String channel,
            @RequestHeader(name = AppConstant.ACCEPT_LANGUAGE,required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = false) String serviceId,
            @RequestHeader(name = AppConstant.SCREENID,required = false) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = false) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = false) String subModuleId
    ) {
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
