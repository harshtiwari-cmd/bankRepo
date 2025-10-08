package com.example.card.adapter.api.controller;


import com.example.card.domain.dto.*;
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

    @PostMapping("/save-new")
    public ResponseEntity<String> saveBankDetailsNew(
            @RequestHeader(name = AppConstant.UNIT, required = false) String unit,
            @RequestHeader(name = AppConstant.CHANNEL, required = false) String channel,
            @RequestHeader(name = AppConstant.ACCEPT_LANGUAGE,required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = false) String serviceId,
            @RequestHeader(name = AppConstant.SCREENID,required = false) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = false) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = false) String subModuleId,
            @RequestBody BankDetailsNewRequestDto dto) {
        try {
            String id = String.valueOf(bankDetailsService.saveBankDetailsNew(dto).getId());
            return ResponseEntity.ok("Bank detail saved with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save bank details");
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
            BankDetailsResponseDto data = bankDetailsService.getBankDetails();
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
