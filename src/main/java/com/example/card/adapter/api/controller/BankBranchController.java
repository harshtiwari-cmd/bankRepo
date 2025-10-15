package com.example.card.adapter.api.controller;

import com.example.card.domain.dto.BankBranchDTO;
import com.example.card.domain.dto.BranchValidateRequest;
import com.example.card.domain.dto.CreateBankHarshBranchDTO;
import com.example.card.exceptions.BranchClosedException;

import com.example.card.adapter.api.services.BankBranchService;
import com.example.card.infrastructure.common.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class BankBranchController {

    @Autowired
    private BankBranchService bankBranchService;


    @GetMapping("/branches")
    public ResponseEntity<List<BankBranchDTO>> getAllBranches(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId
    ) {
        log.info("Received request to fetch all bank branches");
        try {
            List<BankBranchDTO> branches = bankBranchService.getAllBranches();

            if (branches.isEmpty()) {
                log.warn("No bank branches found");
                return ResponseEntity.noContent().build();
            }
            log.info("Fetched {} bank branches", branches.size());
            return ResponseEntity.ok(branches);
        } catch (final Exception e) {
            log.error("Error while fetching bank branches: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/branches")

    public ResponseEntity<BankBranchDTO> addBranch(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @RequestBody CreateBankHarshBranchDTO createDTO) {
        log.info("Received request to create a new bank branch: {}", createDTO.getBankBranchName());
        try {
            BankBranchDTO savedBranch = bankBranchService.createBankBranch(createDTO);
            return ResponseEntity.ok(savedBranch);
        } catch (final Exception e) {
            log.error("Error while creating bank branch: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/branches/{id}/validate")
    public ResponseEntity<Boolean> validateBranchOpen(
            @PathVariable final Long id,
            @RequestBody final BranchValidateRequest request) {
        log.info("Validating if branch with ID {} is open at {}", id, request.getDateTime());

        boolean open = bankBranchService.isBranchOpen(id, request.getDateTime());
        if (!open) {
            log.warn("Branch with ID {} is closed at {}", id, request.getDateTime());
            throw new BranchClosedException("Branch is closed on the requested date/time.");
        }
        log.info("Branch with ID {} is open at {}", id, request.getDateTime());
        return ResponseEntity.ok(true);
    }

}
