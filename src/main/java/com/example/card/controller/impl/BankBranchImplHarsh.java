package com.example.card.controller.impl;

import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.constrants.dto.BranchValidateRequest;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import com.example.card.exceptions.BranchClosedException;
import com.example.card.services.BankBranchServiceHarsh;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankBranchImplHarsh {

    @Autowired
    private BankBranchServiceHarsh bankBranchServiceHarsh;

    @GetMapping("/branches")
    public ResponseEntity<List<BankBranchHarshDTO>> getAllBranches() {
        try {
            final List<BankBranchHarshDTO> branches = this.bankBranchServiceHarsh.getAllBranches();

            if (branches.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(branches);
        } catch (final Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/branches")
    public ResponseEntity<BankBranchHarshDTO> addBranch(@RequestBody @Valid final CreateBankHarshBranchDTO createDTO) {

        try {
            final BankBranchHarshDTO savedBranch = this.bankBranchServiceHarsh.createBankBranch(createDTO);
            return ResponseEntity.ok(savedBranch);
        } catch (final Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/branches/{id}/validate")
    public ResponseEntity<Boolean> validateBranchOpen(
            @PathVariable final Long id,
            @RequestBody final BranchValidateRequest request) {

        final boolean open = this.bankBranchServiceHarsh.isBranchOpen(id, request.getDateTime());
        if (!open) {
            throw new BranchClosedException("Branch is closed on the requested date/time.");
        }
        return ResponseEntity.ok(true);
    }

}
