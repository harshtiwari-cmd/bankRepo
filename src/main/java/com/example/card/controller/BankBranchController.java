package com.example.card.controller;

import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankBranchDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api")
public interface BankBranchController {

    @GetMapping("/branches")
    ResponseEntity<List<BankBranchDTO>> getAllBranches();

    @PostMapping("/branches")
    ResponseEntity<BankBranchDTO> addBranch(@RequestBody CreateBankBranchDTO createDTO);
}