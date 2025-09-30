package com.example.card.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceResponse {
    private String referenceNumber;
    private String message;
    public ReferenceResponse(String referenceNumber, String message) {
        this.referenceNumber = referenceNumber;
        this.message = message;
    }
}
