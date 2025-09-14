package com.example.card.controller.impl;

import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import com.example.card.services.BankBranchServiceHarsh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankBranchImplHarsh {

    @Autowired
    private BankBranchServiceHarsh bankBranchServiceHarsh;

    @GetMapping("/branches")
    public ResponseEntity<List<BankBranchHarshDTO>> getAllBranches() {
        try {
            List<BankBranchHarshDTO> branches = bankBranchServiceHarsh.getAllBranches();

            if (branches.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(branches);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/branches")
    public ResponseEntity<BankBranchHarshDTO> addBranch(CreateBankHarshBranchDTO createDTO) {
        try {
            BankBranchHarshDTO savedBranch = bankBranchServiceHarsh.createBankBranch(createDTO);
            return ResponseEntity.ok(savedBranch);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
