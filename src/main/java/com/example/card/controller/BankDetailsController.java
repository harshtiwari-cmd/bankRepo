package com.example.card.controller;


import com.example.card.dto.BankDetailsDto;
import com.example.card.entity.BankDetailsEntity;
import org.springframework.http.ResponseEntity;

public interface BankDetailsController {

    public ResponseEntity<BankDetailsEntity> saveBankDetails(BankDetailsDto dto);

}
