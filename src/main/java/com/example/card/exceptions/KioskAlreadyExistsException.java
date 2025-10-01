package com.example.card.exceptions;

public class KioskAlreadyExistsException extends RuntimeException {

    public KioskAlreadyExistsException(String message) {
        super(message);
    }
}
