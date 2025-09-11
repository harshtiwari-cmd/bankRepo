package com.example.card.controller.impl;

import com.example.card.constrants.model.ReferenceResponse;
import com.example.card.controller.IbNumberGeneration;
import com.example.card.services.ReferenceNumberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IbNumberGenerationImpl implements IbNumberGeneration {
    private final ReferenceNumberService referenceNumberService;

    public IbNumberGenerationImpl(ReferenceNumberService referenceNumberService) {
        this.referenceNumberService = referenceNumberService;
    }
    @Override
    public ResponseEntity<ReferenceResponse> generate(String channel) {
        if (channel == null || channel.trim().isEmpty()) {
            ReferenceResponse error = new ReferenceResponse(null, "Channel must be provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        try {
            String refNum = referenceNumberService.generateReferenceNumber(channel);
            ReferenceResponse response = new ReferenceResponse(refNum, "Reference generated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ReferenceResponse error = new ReferenceResponse(null, "An error occurred while generating the reference number");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
