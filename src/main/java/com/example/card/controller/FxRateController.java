package com.example.card.controller;

import com.example.card.dto.FXRateDto;
import com.example.card.entity.FxRate;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.FXRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/fx-rate/api")
@RestController
public class FxRateController {

    private final FXRateService fxRateService;

    public FxRateController(FXRateService fxRateService) {
        this.fxRateService = fxRateService;
    }

    @PostMapping
    public ResponseEntity<FXRateDto> saveFxRate(@RequestBody FXRateDto dto) throws ResourceNotFoundException
    {
        return  new ResponseEntity<>(fxRateService.createFxRate(dto), HttpStatus.CREATED);

    }
}
