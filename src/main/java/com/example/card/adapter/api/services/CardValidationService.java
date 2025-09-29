package com.example.card.adapter.api.services;

import com.example.card.domain.model.CardRequest;

public interface CardValidationService {
    boolean validate(CardRequest request);
}
