package com.example.card.controller.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.controller.AtmController;
import com.example.card.services.AtmService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/atms")
public class AtmControllerImpl implements AtmController {

    private final AtmService iAtmService;

    public AtmControllerImpl(AtmService iAtmService) {
        this.iAtmService = iAtmService;
    }

    @PostMapping
    public ResponseEntity<AtmResponseDto> registerAtm(@RequestBody AtmRequestDto atmRequestDto)  throws ConfigDataResourceNotFoundException
    {

        return  ResponseEntity.ok(iAtmService.registerAtm(atmRequestDto));
    }
}
