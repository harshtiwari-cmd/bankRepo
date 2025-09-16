package com.example.card.controller.impl;

import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.constrants.dto.BranchValidateRequest;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import com.example.card.exceptions.BranchClosedException;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.BankBranchServiceHarsh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<BankBranchHarshDTO> addBranch(@RequestBody CreateBankHarshBranchDTO createDTO) {
        try {
            BankBranchHarshDTO savedBranch = bankBranchServiceHarsh.createBankBranch(createDTO);
            return ResponseEntity.ok(savedBranch);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/branches/{id}/validate")
    public ResponseEntity<Boolean> validateBranchOpen(
            @PathVariable Long id,
            @RequestBody BranchValidateRequest request) {

        boolean open = bankBranchServiceHarsh.isBranchOpen(id, request.getDateTime());
        if (!open) {
            throw new BranchClosedException("Branch is closed on the requested date/time.");
        }
        return ResponseEntity.ok(true);
    }

}
