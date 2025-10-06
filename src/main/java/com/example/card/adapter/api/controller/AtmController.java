package com.example.card.adapter.api.controller;

import com.example.card.domain.dto.AtmRequestDto;
import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.adapter.api.services.AtmService;
import com.example.card.domain.dto.GenericResponse;
import com.example.card.domain.dto.Status;
import com.example.card.infrastructure.common.AppConstant;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<GenericResponse<List<AtmResponseDto>>> getAllAtms(
            @RequestHeader(name = AppConstant.UNIT, required = false) String unit,
            @RequestHeader(name = AppConstant.CHANNEL, required = false) String channel,
            @RequestHeader(name = AppConstant.ACCEPT_LANGUAGE,required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = false) String serviceId,
            @RequestHeader(name = AppConstant.SCREENID,required = false) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = false) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = false) String subModuleId
    ) {
        log.info("Received request to fetch all ATM locations");

        try {
            List<AtmResponseDto> atms = atmService.getAtm();

            if (atms.isEmpty()) {
                log.warn("Failed to load: no data found");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GenericResponse<>(new Status("000404", "No Data Found"), new ArrayList<>()));
            }


            GenericResponse<List<AtmResponseDto>> response = new GenericResponse<>(new Status("000000", "SUCCESS"), atms);
            log.info("Successfully fetched all data");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Failed to fetch ATMs: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("ATM_ERROR", "Failed to fetch ATMs"), null));

        }

    }
}
