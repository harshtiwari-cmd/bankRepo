package com.example.card.constrants.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardResponse {
    private String status;
    private String message;

    public CardResponse() {}

    public CardResponse(final String status, final String message) {
        this.status = status;
        this.message = message;
    }
}
