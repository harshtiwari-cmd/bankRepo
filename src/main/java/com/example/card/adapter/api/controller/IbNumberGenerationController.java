package com.example.card.adapter.api.controller;

import com.example.card.domain.model.ReferenceResponse;
import com.example.card.exceptions.BusinessException;
import com.example.card.adapter.api.services.ReferenceNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reference")
public class IbNumberGenerationController {
    private final ReferenceNumberService referenceNumberService;

    public IbNumberGenerationController(ReferenceNumberService referenceNumberService) {
        this.referenceNumberService = referenceNumberService;
    }

    @GetMapping("/generate")
    public ResponseEntity<ReferenceResponse> generate(@RequestParam String channel) {

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
