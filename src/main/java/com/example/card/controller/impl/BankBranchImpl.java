package com.example.card.controller.impl;

import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankBranchDTO;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.BankBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankBranchImpl {

    @Autowired
    private BankBranchService bankBranchService;

    @GetMapping("/branches")
    public ResponseEntity<List<BankBranchDTO>> getAllBranches() {
        List<BankBranchDTO> branches = bankBranchService.getAllBranches();

        if (branches.isEmpty()) {
            throw new ResourceNotFoundException("No branches found");
        }

        return ResponseEntity.ok(branches);
    }
    @PostMapping("/branches")
    public ResponseEntity<BankBranchDTO> addBranch(@RequestBody CreateBankBranchDTO createDTO) {
        System.out.println(createDTO.getBankBranchType());
        System.out.println(createDTO.toString());

        BankBranchDTO savedBranch = bankBranchService.createBankBranch(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBranch);
    }




}
