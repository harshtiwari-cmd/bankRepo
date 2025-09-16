package com.example.card.exceptions;

public class BranchClosedException extends RuntimeException {
    public BranchClosedException(String message) {
        super(message);
    }
}
