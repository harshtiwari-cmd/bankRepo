package com.example.card.constrants.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponse {
    private String status;
    private String message;

    public CardResponse() {}
    public CardResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
