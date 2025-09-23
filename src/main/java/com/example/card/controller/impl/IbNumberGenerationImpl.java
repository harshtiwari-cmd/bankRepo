package com.example.card.controller.impl;

import com.example.card.constrants.model.ReferenceResponse;
import com.example.card.controller.IbNumberGeneration;
import com.example.card.exceptions.BusinessException;
import com.example.card.services.ReferenceNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IbNumberGenerationImpl implements IbNumberGeneration {
    private final ReferenceNumberService referenceNumberService;

    public IbNumberGenerationImpl(ReferenceNumberService referenceNumberService) {
        this.referenceNumberService = referenceNumberService;
    }

    @Override
    public ResponseEntity<ReferenceResponse> generate(String channel) {

        log.info("GET /generate/reference - Received request to generate reference number");

        if (channel == null || channel.trim().isEmpty()) {

            log.warn("Validation failed: channel is null");

            throw new BusinessException(
                    "CHANNEL_REQUIRED",
                    "Channel must be provided",
                    HttpStatus.BAD_REQUEST
            );
        }

        String refNum = referenceNumberService.generateReferenceNumber(channel);
        log.info("Successfully generated reference number: {}", refNum);
        ReferenceResponse response = new ReferenceResponse(refNum, "Reference generated successfully");
        return ResponseEntity.ok(response);
    }
}
