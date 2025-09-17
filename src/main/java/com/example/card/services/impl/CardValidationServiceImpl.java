package com.example.card.services.impl;

import com.example.card.constrants.model.CardRequest;
import com.example.card.exceptions.BusinessException;
import com.example.card.services.CardValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CardValidationServiceImpl implements CardValidationService {

    @Override
    public boolean validate(CardRequest request) {
        if (request == null) {
            throw new BusinessException(
                    "VALIDATION_ERROR",
                    "Request cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (request.getCardNumber() == null || request.getCardNumber().trim().isEmpty()) {
            throw new BusinessException(
                    "VALIDATION_ERROR",
                    "Card number cannot be null or empty",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (request.getCardNumber().length() != 16) {
            return false;
        }
        return true;
    }
}
