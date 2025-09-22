package com.example.card.controller.impl;

import com.example.card.constrants.dto.*;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.BankDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequestMapping("/bank-details")
@RestController
public class BankDetailsControllerImpl  {

    private final BankDetailsService bankDetailsService;

    public BankDetailsControllerImpl(BankDetailsService bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveBankDetails( @RequestBody  BankDetailsDto dto) throws ResourceNotFoundException {

        String  saved=  bankDetailsService.createBankDetails(dto).getName();
        return new ResponseEntity<String>("bank detail saved with name:"+saved,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<GenericResponse<BankDetailsResponseDto>> getBankDetails() {
        try {
            BankDetailsResponseDto data = bankDetailsService.getbankDetails();
            GenericResponse<BankDetailsResponseDto> response =
                    new GenericResponse<>(new Status("000000", "SUCCESS"), data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("G-00001", "Internal Server ERROR"), null));
        }
    }
}
