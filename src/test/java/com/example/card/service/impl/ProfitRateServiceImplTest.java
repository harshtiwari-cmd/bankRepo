package com.example.card.service.impl;

import com.example.card.domain.dto.ProfitRateRequestDTO;
import com.example.card.domain.dto.ProfitRateResponseDTO;
import com.example.card.repository.ProfitRateRepository;
import com.example.card.adapter.api.services.impl.ProfitRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfitRateServiceImplTest {

    @Autowired
    private ProfitRateServiceImpl profitRateService;

    @Autowired
    private ProfitRateRepository profitRateRepository;

    @BeforeEach
    void setUp() {
        profitRateRepository.deleteAll();
    }

    @Test
    void createProfitRate_ShouldSaveAndReturnDto() {
        ProfitRateRequestDTO request = new ProfitRateRequestDTO();
        request.setProductName("Standard Product");
        request.setRate(5.5);
        request.setEffectiveFrom(LocalDate.now());
        request.setEffectiveTo(LocalDate.now().plusMonths(6));
        request.setIsFixed(true);
        request.setDescription("Standard profit rate");

        ProfitRateResponseDTO response = profitRateService.createProfitRate(request);

        assertNotNull(response);
        assertEquals("Standard Product", response.getProductName());
        assertEquals(5.5, response.getRate());
        assertEquals(true, response.getIsFixed());
        assertEquals("Standard profit rate", response.getDescription());


        assertEquals(1, profitRateRepository.count());
    }

    @Test
    void getAllProfitRates_ShouldReturnAllSavedRates() {
        ProfitRateRequestDTO request1 = new ProfitRateRequestDTO();
        request1.setProductName("Standard Product");
        request1.setRate(5.5);
        request1.setEffectiveFrom(LocalDate.now());
        request1.setEffectiveTo(LocalDate.now().plusMonths(6));
        request1.setIsFixed(true);
        request1.setDescription("Standard profit rate");

        ProfitRateRequestDTO request2 = new ProfitRateRequestDTO();
        request2.setProductName("Premium Product");
        request2.setRate(7.0);
        request2.setEffectiveFrom(LocalDate.now().plusDays(1));
        request2.setEffectiveTo(LocalDate.now().plusMonths(12));
        request2.setIsFixed(false);
        request2.setDescription("Premium profit rate");

        profitRateService.createProfitRate(request1);
        profitRateService.createProfitRate(request2);

        List<ProfitRateResponseDTO> allRates = profitRateService.getAllProfitRates();

        assertEquals(2, allRates.size());
        assertTrue(allRates.stream().anyMatch(r -> r.getProductName().equals("Standard Product")));
        assertTrue(allRates.stream().anyMatch(r -> r.getProductName().equals("Premium Product")));
    }
}
