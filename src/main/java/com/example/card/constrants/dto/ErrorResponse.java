package com.example.card.constrants.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int code;
    private String message;
    private String details;

    public ErrorResponse(int code, String message, String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
}