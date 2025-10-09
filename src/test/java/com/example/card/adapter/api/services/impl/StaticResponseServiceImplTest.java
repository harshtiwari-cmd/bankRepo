package com.example.card.adapter.api.services.impl;

import com.example.card.domain.model.CardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StaticResponseServiceImplTest {

    private StaticResponseServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new StaticResponseServiceImpl();
        service.init();
    }

    @Test
    void testSuccessResponseLoaded() {
        CardResponse success = service.getSuccessResponse();
        assertNotNull(success);
        assertEquals("SUCCESS", success.getStatus());
        assertEquals("Card is Valid", success.getMessage());
    }

    @Test
    void testFailureResponseLoaded() {
        CardResponse failure = service.getFailureResponse();
        assertNotNull(failure);
        assertEquals("FAILURE", failure.getStatus());
        assertEquals("Card validation failed due to invalid input data", failure.getMessage());
    }
}


