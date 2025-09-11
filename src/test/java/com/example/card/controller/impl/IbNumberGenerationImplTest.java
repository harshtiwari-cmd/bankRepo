package com.example.card.controller.impl;

import com.example.card.constrants.model.ReferenceResponse;
import com.example.card.services.ReferenceNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IbNumberGenerationImplTest {

    private ReferenceNumberService referenceNumberService;
    private IbNumberGenerationImpl controller;

    @BeforeEach
    void setUp() {
        referenceNumberService = mock(ReferenceNumberService.class);
        controller = new IbNumberGenerationImpl(referenceNumberService);
    }

    @Test
    void generate_withValidChannel_returnsReference() {
        String channel = "XYZ";
        String refNumber = "XYZ01012023000001";
        when(referenceNumberService.generateReferenceNumber(channel)).thenReturn(refNumber);

        ResponseEntity<ReferenceResponse> responseEntity = controller.generate(channel);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(refNumber, responseEntity.getBody().getReferenceNumber());
        assertEquals("Reference generated successfully", responseEntity.getBody().getMessage());
    }

    @Test
    void generate_withNullChannel_returnsBadRequest() {
        ResponseEntity<ReferenceResponse> responseEntity = controller.generate(null);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNull(responseEntity.getBody().getReferenceNumber());
        assertEquals("Channel must be provided", responseEntity.getBody().getMessage());
    }

    @Test
    void generate_withEmptyChannel_returnsBadRequest() {
        ResponseEntity<ReferenceResponse> responseEntity = controller.generate("  ");

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNull(responseEntity.getBody().getReferenceNumber());
        assertEquals("Channel must be provided", responseEntity.getBody().getMessage());
    }

    @Test
    void generate_whenServiceThrowsException_returnsInternalServerError() {
        String channel = "ABC";
        when(referenceNumberService.generateReferenceNumber(channel)).thenThrow(new RuntimeException("fail"));

        ResponseEntity<ReferenceResponse> responseEntity = controller.generate(channel);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNull(responseEntity.getBody().getReferenceNumber());
        assertEquals("An error occurred while generating the reference number", responseEntity.getBody().getMessage());
    }
}
