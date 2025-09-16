package com.example.card.controller.impl;

import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.dto.BankDetailsDto;
import com.example.card.entity.BankDetailsEntity;
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
    public ResponseEntity<String> saveBankDetails(@RequestParam String bankId, @RequestBody   BankDetailsDto dto) throws ResourceNotFoundException {
        dto.setBankId(bankId);
         BankDetailsEntity saved=  bankDetailsService.createBankDetails(dto);
        return ResponseEntity.ok("bank details saved for bankId :"+saved.getBankId());
    }
    @GetMapping
    public ResponseEntity<List<BankDetailsDto>> getBankDetails(@RequestParam String bankId) {
        try {
            List<BankDetailsDto> branches = bankDetailsService.getbankDetails(bankId);

            if (branches.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(branches);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
