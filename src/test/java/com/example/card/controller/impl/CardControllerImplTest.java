package com.example.card.controller.impl;

import com.example.card.adapter.api.controller.CardController;
import com.example.card.domain.model.CardRequest;
import com.example.card.domain.model.CardResponse;
import com.example.card.adapter.api.services.CardValidationService;
import com.example.card.adapter.api.services.StaticResponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardControllerImplTest {

    @Mock
    private CardValidationService validationService;

    @Mock
    private StaticResponseService staticResponseService;

    @InjectMocks
    private CardController cardController;

    private CardRequest validRequest;
    private CardResponse successResponse;
    private CardResponse failureResponse;

    @BeforeEach
    void setUp() {
        validRequest = new CardRequest();
        validRequest.setCardNumber("1234567812345678");
        validRequest.setCardHolderName("John Doe");

        successResponse = new CardResponse("SUCCESS", "Card is valid");
        failureResponse = new CardResponse("FAILURE", "Invalid card");
    }

    @Test
    void validateCard_WithValidRequest_ReturnsOkResponse() {
        when(validationService.validate(validRequest)).thenReturn(true);
        when(staticResponseService.getSuccessResponse()).thenReturn(successResponse);

        ResponseEntity<CardResponse> response = cardController.validateCard(validRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successResponse, response.getBody());
        verify(validationService).validate(validRequest);
        verify(staticResponseService).getSuccessResponse();
    }

    @Test
    void validateCard_WithInvalidRequest_ReturnsBadRequestResponse() {
        when(validationService.validate(validRequest)).thenReturn(false);
        when(staticResponseService.getFailureResponse()).thenReturn(failureResponse);

        ResponseEntity<CardResponse> response = cardController.validateCard(validRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(failureResponse, response.getBody());
        verify(validationService).validate(validRequest);
        verify(staticResponseService).getFailureResponse();
    }

    @Test
    void validateCard_WhenValidationServiceThrowsException_Propagates() {
        when(validationService.validate(validRequest)).thenThrow(new RuntimeException("Validation failed"));

        assertThrows(RuntimeException.class, () -> cardController.validateCard(validRequest));
        verify(validationService).validate(validRequest);
        verifyNoInteractions(staticResponseService);
    }
}
