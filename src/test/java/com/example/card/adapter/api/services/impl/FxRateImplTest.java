package com.example.card.adapter.api.services.impl;

import com.example.card.domain.dto.FXRateDto;
import com.example.card.domain.dto.FXRateResponseDto;
import com.example.card.repository.FxRateRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FxRateImplTest {

    @Autowired
    private FxRateImpl fxRateService;

    @Autowired
    private FxRateRepo fxRateRepo;

    @BeforeEach
    void setUp() {
        fxRateRepo.deleteAll();
    }

    @Test
    void createFxRate_ShouldSaveAndReturnDto() {
        FXRateDto dto = new FXRateDto("US", "United States", "USD", new BigDecimal("82.50"), new BigDecimal("83.00"));

        FXRateDto savedDto = fxRateService.createFxRate(dto);

        assertNotNull(savedDto);
        assertEquals("US", savedDto.getCountryCode());
        assertEquals("United States", savedDto.getCountryName());
        assertEquals("USD", savedDto.getCurrencyCode());
        assertEquals(new BigDecimal("82.50"), savedDto.getBuyRate());
        assertEquals(new BigDecimal("83.00"), savedDto.getSellRate());

        assertEquals(1, fxRateRepo.count());
    }

    @Test
    void getFx_ShouldReturnAllSavedRates() {
        FXRateDto dto1 = new FXRateDto("US", "United States", "USD", new BigDecimal("82.50"), new BigDecimal("83.00"));
        FXRateDto dto2 = new FXRateDto("IN", "India", "INR", new BigDecimal("1.00"), new BigDecimal("1.01"));

        fxRateService.createFxRate(dto1);
        fxRateService.createFxRate(dto2);

        List<FXRateResponseDto> rates = fxRateService.getFx();

        assertEquals(2, rates.size());
        assertTrue(rates.stream().anyMatch(r -> r.getCountryCode().equals("US")));
        assertTrue(rates.stream().anyMatch(r -> r.getCountryCode().equals("IN")));
    }
}

