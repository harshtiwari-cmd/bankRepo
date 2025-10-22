package com.example.card.adapter.api.controller;

import com.example.card.domain.dto.FXRateDto;
import com.example.card.domain.dto.FXRateResponseDto;
import com.example.card.domain.dto.GenericResponse;
import com.example.card.domain.dto.Status;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.adapter.api.services.FXRateService;
import com.example.card.infrastructure.common.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class FxRateController {

    private final FXRateService fxRateService;

    public FxRateController(FXRateService fxRateService) {
        this.fxRateService = fxRateService;
    }

    @PostMapping("/save-fx-rates")
    public ResponseEntity<FXRateDto> saveFxRate(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @RequestBody FXRateDto dto) throws ResourceNotFoundException {
        log.info("Received request to save FX rate for country code: {}", dto != null ? dto.getCountryCode() : "null");

        try {
            FXRateDto saved = fxRateService.createFxRate(dto);
            log.info("FX rate saved successfully for country: {} (Currency: {})", saved.getCountryName(), saved.getCurrencyCode());
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            log.error("Resource not found while saving FX rate: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while saving FX rate: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view-fx-rates")
    public ResponseEntity<GenericResponse<List<FXRateResponseDto>>> getService(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId
    ) {
        log.info("Received request to fetch FX rates");

        try {
            List<FXRateResponseDto> fxRates = fxRateService.getFx();

            if (fxRates.isEmpty()) {
                log.warn("No FX rate data found in the system");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GenericResponse<>(new Status("000404", "No Data Found"), new ArrayList<>()));
            }

            log.info("Fetched {} FX rate records", fxRates.size());

            GenericResponse<List<FXRateResponseDto>> response =
                    new GenericResponse<>(new Status("000000", "SUCCESS"), fxRates);

            log.debug("FX rate response payload: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error while fetching FX rates: {}", e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("000500", "Internal Server ERROR"), null));
        }
    }
}
