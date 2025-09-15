package com.example.card.exceptions;

public class ResourceNotFoundException extends  RuntimeException{


    public ResourceNotFoundException(String message) {
        super(message);
    }
}
