package com.example.card.services.impl;

import com.example.card.constrants.model.CardRequest;
import com.example.card.exceptions.BusinessException;
import com.example.card.services.CardValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CardValidationServiceImpl implements CardValidationService {

    @Override
    public boolean validate(CardRequest request) {

        log.info("processing to validate card");
        if (request == null) {
            log.warn("Validation Failed: card request is null");
            throw new BusinessException(
                    "VALIDATION_ERROR",
                    "Request cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (request.getCardNumber() == null || request.getCardNumber().trim().isEmpty()) {
            log.warn("Validation failed: card number is null or empty");
            throw new BusinessException(
                    "VALIDATION_ERROR",
                    "Card number cannot be null or empty",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (request.getCardNumber().length() != 16) {
            log.warn("Validation failed: card must be 16 digit");
            return false;
        }
        return true;
    }
}
