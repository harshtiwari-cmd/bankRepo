package com.example.card.controller.impl;

import com.example.card.dto.BankDetailsDto;
import com.example.card.entity.BankDetailsEntity;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.BankDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/bank-details/api")
@RestController
public class BankDetailsControllerImpl  {

    private final BankDetailsService bankDetailsService;

    public BankDetailsControllerImpl(BankDetailsService bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @PostMapping
    public ResponseEntity<String> saveBankDetails(@RequestParam String bankId, @RequestBody   BankDetailsDto dto) throws ResourceNotFoundException {
        dto.setBankId(bankId);
         BankDetailsEntity saved=  bankDetailsService.createBankDetails(dto);
        return ResponseEntity.ok("bank details saved for bankId :"+saved.getBankId());
    }
}
