package com.example.card.controller;

import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api")
public interface BankBranchHarshController {

    @GetMapping("/branches")
    ResponseEntity<List<BankBranchHarshDTO>> getAllBranches();

    @PostMapping("/branches")
    ResponseEntity<BankBranchHarshDTO> addBranch(@RequestBody CreateBankHarshBranchDTO createDTO);
}