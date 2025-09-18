package com.example.card.controller.impl;

import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.BranchValidateRequest;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import com.example.card.exceptions.BranchClosedException;

import com.example.card.services.BankBranchService;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            List<BankBranchDTO> branches = bankBranchService.getAllBranches();

            if (branches.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(branches);
        } catch (final Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/branches")

    public ResponseEntity<BankBranchDTO> addBranch(@RequestBody CreateBankHarshBranchDTO createDTO) {
        try {
            BankBranchDTO savedBranch = bankBranchService.createBankBranch(createDTO);
            return ResponseEntity.ok(savedBranch);
        } catch (final Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/branches/{id}/validate")
    public ResponseEntity<Boolean> validateBranchOpen(
            @PathVariable final Long id,
            @RequestBody final BranchValidateRequest request) {

        boolean open = bankBranchService.isBranchOpen(id, request.getDateTime());
        if (!open) {
            throw new BranchClosedException("Branch is closed on the requested date/time.");
        }
        return ResponseEntity.ok(true);
    }

}
