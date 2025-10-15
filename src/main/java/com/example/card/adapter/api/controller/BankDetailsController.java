package com.example.card.adapter.api.controller;


import com.example.card.domain.dto.*;
import com.example.card.adapter.api.services.BankDetailsService;
import com.example.card.domain.model.CardBinAllWrapper;
import com.example.card.infrastructure.common.AppConstant;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/bank-details")
@RestController
public class BankDetailsController {

    @Autowired
    private BankDetailsService bankDetailsService;

    @PostMapping("/save-new")
    public ResponseEntity<String> saveBankDetailsNew(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @RequestBody BankDetailsNewRequestDto dto) {
        try {
            String id = String.valueOf(bankDetailsService.saveBankDetailsNew(dto).getId());
            return ResponseEntity.ok("Bank detail saved with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save bank details");
        }
    }


    @PostMapping
    public ResponseEntity<GenericResponse<BankDetailsResponseDto>> getBankDetails(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @Valid @RequestBody(required = true)CardBinAllWrapper wrapper
            ) {
        log.info("Received request to fetch bank details");

        try {
            if ("en".equalsIgnoreCase(lang == null ? "" : lang.trim()) ||    "ar".equalsIgnoreCase(lang == null ? "" : lang.trim()))  {
                BankDetailsResponseDto data = bankDetailsService.getBankDetails(lang);
                log.info("Bank details fetched successfully for email: {}", data.getMail());

                GenericResponse<BankDetailsResponseDto> response =
                        new GenericResponse<>(new Status("000000", "SUCCESS"), data);

                return ResponseEntity.ok(response);
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new GenericResponse<>(new Status("G-00000", "use lang ar or en."), null));
            }
        } catch (Exception e) {
            log.error("Error fetching bank details: {}", e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("G-00001", "Internal Server ERROR"), null));
        }
    }

}