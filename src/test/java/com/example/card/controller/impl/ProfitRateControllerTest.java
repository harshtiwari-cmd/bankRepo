package com.example.card.controller.impl;

import com.example.card.constrants.dto.ProfitRateRequestDTO;
import com.example.card.constrants.dto.ProfitRateResponseDTO;
import com.example.card.controller.ProfitRateController;
import com.example.card.services.ProfitRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfitRateController.class)
class ProfitRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfitRateService profitRateService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getAllProfitRates_ShouldReturnOkAndList() throws Exception {
        ProfitRateResponseDTO dto1 = new ProfitRateResponseDTO();
        dto1.setId(1L);
        dto1.setProductName("Savings Plan");
        dto1.setRate(5.5);
        dto1.setEffectiveFrom(LocalDate.of(2025, 9, 24));
        dto1.setIsFixed(true);

        ProfitRateResponseDTO dto2 = new ProfitRateResponseDTO();
        dto2.setId(2L);
        dto2.setProductName("Current Plan");
        dto2.setRate(3.5);
        dto2.setEffectiveFrom(LocalDate.of(2025, 10, 1));
        dto2.setIsFixed(false);

        List<ProfitRateResponseDTO> profitRates = Arrays.asList(dto1, dto2);

        when(profitRateService.getAllProfitRates()).thenReturn(profitRates);

        mockMvc.perform(get("/api/profit-rates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Savings Plan"))
                .andExpect(jsonPath("$[0].effectiveFrom").value("2025-09-24"))
                .andExpect(jsonPath("$[0].isFixed").value(true))
                .andExpect(jsonPath("$[1].productName").value("Current Plan"))
                .andExpect(jsonPath("$[1].effectiveFrom").value("2025-10-01"))
                .andExpect(jsonPath("$[1].isFixed").value(false));
    }

    @Test
    void createProfitRate_ShouldReturnCreated() throws Exception {
        ProfitRateRequestDTO request = new ProfitRateRequestDTO();
        request.setProductName("Fixed Deposit");
        request.setRate(6.0);
        request.setEffectiveFrom(LocalDate.of(2025, 11, 1));
        request.setIsFixed(true);

        ProfitRateResponseDTO response = new ProfitRateResponseDTO();
        response.setId(10L);
        response.setProductName("Fixed Deposit");
        response.setRate(6.0);
        response.setEffectiveFrom(LocalDate.of(2025, 11, 1));
        response.setIsFixed(true);

        when(profitRateService.createProfitRate(any())).thenReturn(response);

        mockMvc.perform(post("/api/profit-rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.productName").value("Fixed Deposit"))
                .andExpect(jsonPath("$.rate").value(6.0))
                .andExpect(jsonPath("$.effectiveFrom").value("2025-11-01"))
                .andExpect(jsonPath("$.isFixed").value(true));
    }
}
