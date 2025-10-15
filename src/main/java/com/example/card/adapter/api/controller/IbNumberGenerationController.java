package com.example.card.adapter.api.controller;

import com.example.card.domain.model.ReferenceResponse;
import com.example.card.exceptions.BusinessException;
import com.example.card.adapter.api.services.ReferenceNumberService;
import com.example.card.infrastructure.common.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reference")
public class IbNumberGenerationController {
    private final ReferenceNumberService referenceNumberService;

    public IbNumberGenerationController(ReferenceNumberService referenceNumberService) {
        this.referenceNumberService = referenceNumberService;
    }

    @GetMapping("/generate")
    public ResponseEntity<ReferenceResponse> generate(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @RequestParam String channelName) {

        log.info("GET /generate/reference - Received request to generate reference number");

        if (channelName == null || channelName.trim().isEmpty()) {

            log.warn("Validation failed: channelName is null");

            throw new BusinessException(
                    "CHANNEL_REQUIRED",
                    "Channel must be provided",
                    HttpStatus.BAD_REQUEST
            );
        }

        String refNum = referenceNumberService.generateReferenceNumber(channelName);
        log.info("Successfully generated reference number: {}", refNum);
        ReferenceResponse response = new ReferenceResponse(refNum, "Reference generated successfully");
        return ResponseEntity.ok(response);
    }
}
