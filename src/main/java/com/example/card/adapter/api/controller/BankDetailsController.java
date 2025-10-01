package com.example.card.adapter.api.controller;

import com.example.card.domain.dto.BankDetailsDto;
import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.domain.dto.GenericResponse;
import com.example.card.domain.dto.Status;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.adapter.api.services.BankDetailsService;
import com.example.card.infrastructure.common.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/bank-details")
@RestController
public class BankDetailsController {

    private final BankDetailsService bankDetailsService;

    public BankDetailsController(BankDetailsService bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveBankDetails(
            @RequestHeader(name = AppConstant.UNIT, required = false) String unit,
            @RequestHeader(name = AppConstant.CHANNEL, required = false) String channel,
            @RequestHeader(name = AppConstant.ACCEPT_LANGUAGE,required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = false) String serviceId,
            @RequestHeader(name = AppConstant.SCREENID,required = false) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = false) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = false) String subModuleId,
            @RequestBody BankDetailsDto dto) throws ResourceNotFoundException {
        log.info("Received request to save bank details for email: {}", dto != null ? dto.getMail() : "null");

        try {
            String saved = bankDetailsService.createBankDetails(dto).getName();
            log.info("Bank details saved successfully for name: {}", saved);
            return new ResponseEntity<>("Bank detail saved with name: " + saved, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            log.error("Failed to save bank details: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while saving bank details: {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to save bank details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<GenericResponse<BankDetailsResponseDto>> getBankDetails(
            @RequestHeader(name = AppConstant.UNIT, required = false) String unit,
            @RequestHeader(name = AppConstant.CHANNEL, required = false) String channel,
            @RequestHeader(name = AppConstant.ACCEPT_LANGUAGE,required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = false) String serviceId,
            @RequestHeader(name = AppConstant.SCREENID,required = false) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = false) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = false) String subModuleId
    ) {
        log.info("Received request to fetch bank details");

        try {
            BankDetailsResponseDto data = bankDetailsService.getbankDetails();
            log.info("Bank details fetched successfully for email: {}", data.getMail());

            GenericResponse<BankDetailsResponseDto> response =
                    new GenericResponse<>(new Status("000000", "SUCCESS"), data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching bank details: {}", e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("G-00001", "Internal Server ERROR"), null));
        }
    }
}
