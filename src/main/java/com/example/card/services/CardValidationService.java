package com.example.card.services;

import com.example.card.constrants.model.CardRequest;

public interface CardValidationService {
    boolean validate(CardRequest request);
}
