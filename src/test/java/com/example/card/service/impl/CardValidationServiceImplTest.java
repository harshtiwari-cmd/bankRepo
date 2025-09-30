package com.example.card.service.impl;
import com.example.card.domain.model.CardRequest;
import com.example.card.exceptions.BusinessException;
import com.example.card.adapter.api.services.impl.CardValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardValidationServiceImplTest {

    private CardValidationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CardValidationServiceImpl();
    }

    @Test
    void validate_withNullRequest_throwsBusinessException() {
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> service.validate(null)
        );
        assertEquals("Request cannot be null", ex.getMessage());
    }

    @Test
    void validate_withNullCardNumber_throwsBusinessException() {
        CardRequest request = new CardRequest();
        request.setCardNumber(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> service.validate(request)
        );
        assertEquals("Card number cannot be null or empty", ex.getMessage());
    }

    @Test
    void validate_withEmptyCardNumber_throwsBusinessException() {
        CardRequest request = new CardRequest();
        request.setCardNumber("");

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> service.validate(request)
        );
        assertEquals("Card number cannot be null or empty", ex.getMessage());
    }

    @Test
    void validate_withShortCardNumber_returnsFalse() {
        CardRequest request = new CardRequest();
        request.setCardNumber("1234");
        assertFalse(service.validate(request));
    }

    @Test
    void validate_withValidCardNumber_returnsTrue() {
        CardRequest request = new CardRequest();
        request.setCardNumber("1234567812345678");
        assertTrue(service.validate(request));
    }
}
