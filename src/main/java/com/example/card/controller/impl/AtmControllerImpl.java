package com.example.card.controller.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.controller.AtmController;
import com.example.card.services.AtmService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<AtmResponseDto>> getAllBranches() {
        try {
            List<AtmResponseDto> atms = iAtmService.getAtm();

            if (atms.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(atms);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
