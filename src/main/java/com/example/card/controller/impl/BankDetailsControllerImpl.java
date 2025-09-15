package com.example.card.controller.impl;

import com.example.card.controller.BankDetailsController;
import com.example.card.dto.BankDetailsDto;
import com.example.card.entity.BankDetailsEntity;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.BankDetailsService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bank-details/api")
@RestController
public class BankDetailsControllerImpl implements BankDetailsController {

    private final BankDetailsService bankDetailsService;

    public BankDetailsControllerImpl(BankDetailsService bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @PostMapping
    @Override
    public ResponseEntity<BankDetailsEntity> saveBankDetails(@RequestBody BankDetailsDto dto) throws ResourceNotFoundException {
        return new ResponseEntity<>(bankDetailsService.createBankDetails(dto), HttpStatus.CREATED);
    }
}
