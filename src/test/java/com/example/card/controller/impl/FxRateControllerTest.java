package com.example.card.controller.impl;

import com.example.card.domain.dto.FXRateDto;
import com.example.card.domain.dto.FXRateResponseDto;
import com.example.card.domain.dto.GenericResponse;
import com.example.card.adapter.api.controller.FxRateController;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.adapter.api.services.FXRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FxRateControllerTest {

    private FXRateService fxRateService;
    private FxRateController fxRateController;

    private FXRateDto fxRateDto;
    private FXRateResponseDto fxRateResponseDto;

    @BeforeEach
    void setUp() {
        fxRateService = mock(FXRateService.class);
        fxRateController = new FxRateController(fxRateService);

        fxRateDto = new FXRateDto("IN", "India", "INR",
                BigDecimal.valueOf(82.50), BigDecimal.valueOf(83.00));

        fxRateResponseDto = new FXRateResponseDto("IN", "India", "INR",
                BigDecimal.valueOf(82.50), BigDecimal.valueOf(83.00));
    }

    @Test
    void saveFxRate_ShouldReturnCreatedFxRate() throws ResourceNotFoundException {
        when(fxRateService.createFxRate(any(FXRateDto.class)))
                .thenReturn(fxRateDto);

        var response = fxRateController.saveFxRate("unit1", "channel1", "en", "service1", "screen1", "module1", "subModule1", fxRateDto);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("IN", response.getBody().getCountryCode());
        assertEquals("India", response.getBody().getCountryName());

        verify(fxRateService, times(1)).createFxRate(any(FXRateDto.class));
    }

    @Test
    void getService_ShouldReturnSuccessResponse_WhenDataExists() {
        when(fxRateService.getFx()).thenReturn(List.of(fxRateResponseDto));

        var response = fxRateController.getService("unit1", "channel1", "en", "service1", "screen1", "module1", "subModule1");

        assertEquals(200, response.getStatusCodeValue());
        GenericResponse<List<FXRateResponseDto>> body = response.getBody();
        assertNotNull(body);
        assertEquals("000000", body.getStatus().getCode());
        assertEquals("SUCCESS", body.getStatus().getDescription());
        assertEquals(1, body.getData().size());
        assertEquals("IN", body.getData().get(0).getCountryCode());
    }

    @Test
    void getService_ShouldReturnNoDataFound_WhenListIsEmpty() {
        when(fxRateService.getFx()).thenReturn(Collections.emptyList());

        var response = fxRateController.getService("unit1", "channel1", "en", "service1", "screen1", "module1", "subModule1");

        assertEquals(200, response.getStatusCodeValue());
        GenericResponse<List<FXRateResponseDto>> body = response.getBody();
        assertNotNull(body);
        assertEquals("000404", body.getStatus().getCode());
        assertEquals("No Data Found", body.getStatus().getDescription());
        assertTrue(body.getData().isEmpty());
    }

    @Test
    void getService_ShouldReturnInternalServerError_WhenExceptionThrown() {
        when(fxRateService.getFx()).thenThrow(new RuntimeException("DB error"));

        var response = fxRateController.getService("unit1", "channel1", "en", "service1", "screen1", "module1", "subModule1");

        assertEquals(500, response.getStatusCodeValue());
        GenericResponse<List<FXRateResponseDto>> body = response.getBody();
        assertNotNull(body);
        assertEquals("G-00001", body.getStatus().getCode());
        assertEquals("Internal Server ERROR", body.getStatus().getDescription());
        assertNull(body.getData());
    }
}
