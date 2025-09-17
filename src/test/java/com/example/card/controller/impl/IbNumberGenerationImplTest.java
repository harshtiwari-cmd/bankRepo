package com.example.card.controller.impl;

import com.example.card.constrants.model.ReferenceResponse;
import com.example.card.services.ReferenceNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.card.controller.impl.IbNumberGenerationImplTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.card.exceptions.BusinessException;

@ExtendWith(MockitoExtension.class)
class IbNumberGenerationImplTest {

    @Mock
    private ReferenceNumberService referenceNumberService;

    @InjectMocks
    private IbNumberGenerationImpl controller;

    private String validChannel;
    private String generatedRef;

    @BeforeEach
    void setUp() {
        validChannel = "SBI";
        generatedRef = "SBI16092025000001";
    }

    @Test
    void generate_withValidChannel_returnsReference() {

        when(referenceNumberService.generateReferenceNumber(validChannel)).thenReturn(generatedRef);


        ResponseEntity<ReferenceResponse> responseEntity = controller.generate(validChannel);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(generatedRef, responseEntity.getBody().getReferenceNumber());
        assertEquals("Reference generated successfully", responseEntity.getBody().getMessage());

        verify(referenceNumberService, times(1)).generateReferenceNumber(validChannel);
    }





    @Test
    void generate_withNullChannel_throwsBusinessException() {
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> controller.generate(null)
        );

        assertEquals("CHANNEL_REQUIRED", ex.getErrorCode());
        assertEquals("Channel must be provided", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void generate_withEmptyChannel_throwsBusinessException() {
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> controller.generate("  ")
        );

        assertEquals("CHANNEL_REQUIRED", ex.getErrorCode());
        assertEquals("Channel must be provided", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void generate_whenServiceThrowsException_propagatesException() {
        String channel = "ABC";
        when(referenceNumberService.generateReferenceNumber(channel))
                .thenThrow(new RuntimeException("Unexpected DB error"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> controller.generate(channel)
        );

        assertEquals("Unexpected DB error", ex.getMessage());
    }

}
