package com.example.card.controller.impl;

import com.example.card.constrants.dto.BankDetailsDto;
import com.example.card.constrants.dto.BankDetailsResponseDto;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.BankDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<BankDetailsResponseDto> getBankDetails() {
       return new ResponseEntity<>(bankDetailsService.getbankDetails(),HttpStatus.CREATED);


    }
}
