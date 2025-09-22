package com.example.card.controller;

import com.example.card.constrants.dto.*;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.FXRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
public class FxRateController {

    private final FXRateService fxRateService;

    public FxRateController(FXRateService fxRateService) {
        this.fxRateService = fxRateService;
    }

    @PostMapping("/save-fx-rates")
    public ResponseEntity<FXRateDto> saveFxRate(@RequestBody FXRateDto dto) throws ResourceNotFoundException
    {
        return  new ResponseEntity<>(fxRateService.createFxRate(dto), HttpStatus.CREATED);

    }

    @GetMapping("/view-fx-rates")
    public ResponseEntity<GenericResponse<List<FXRateResponseDto>>> getService() {
        try {
            List<FXRateResponseDto> fxRates = fxRateService.getFx();

            if (fxRates.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GenericResponse<>(new Status("000404", "No Data Found"), new ArrayList<>()));
            }

            GenericResponse<List<FXRateResponseDto>> response =
                    new GenericResponse<>(new Status("000000", "SUCCESS"), fxRates);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("G-00001", "Internal Server ERROR"), null));
        }
    }
}
