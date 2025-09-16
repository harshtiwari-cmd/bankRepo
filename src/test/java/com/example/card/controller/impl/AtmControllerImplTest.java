package com.example.card.controller.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.services.AtmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AtmControllerImpl.class)
public class AtmControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AtmService service;

    private AtmRequestDto requestDto;
    private AtmResponseDto responseDto;

    @BeforeEach
    public void setUp() {
        this.requestDto = AtmRequestDto.builder()
                .atmId("ATM12345")
                .branchId("BR001")
                .siteName("Central Mall ATM")
                .streetName("MG Road")
                .townName("Indore")
                .country("India")
                .postCode("452001")
                .latitude(22.7160)
                .longitude(75.5050)
                .supportedLanguages(Arrays.asList("English", "Hindi"))
                .atmServices(Arrays.asList("Case Withdrawal", "Balance Inquiry", "Mini Statement"))
                .supportedCurrencies(Arrays.asList("INR", "USD"))
                .minimumPossibleAmount(1000)
                .openTime("09:00")
                .build();

        this.responseDto = AtmResponseDto.builder()
                .atmId("ATM12345")
                .branchId("BR001")
                .siteName("Central Mall ATM")
                .townName("Indore")
                .country("India")
                .openTime("09:00")
                .build();
    }

    @Test
    public void shouldReturnAtmDetails() throws Exception {
        Mockito.when(this.service.registerAtm(this.requestDto)).thenReturn(this.responseDto);

        this.mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.atmId").value("ATM12345"))
                .andExpect(jsonPath("$.branchId").value("BR001"))
                .andExpect(jsonPath("$.openTime").value("09:00"));
    }

    @Test
    public void shouldReturn400_whenRequestIsInvalid() throws Exception {
        this.requestDto.setAtmId(null);

        this.mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.requestDto)))
                .andExpect(status().isBadRequest());
    }


}