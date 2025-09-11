package com.example.card.services.impl;

import com.example.card.constrants.model.CardRequest;
import com.example.card.services.CardValidationService;
import org.springframework.stereotype.Service;

@Service
public class CardValidationServiceImpl implements CardValidationService {
    @Override
    public boolean validate(CardRequest request) {
        return request.getCardNumber() != null && request.getCardNumber().length() == 16;
    }
}
