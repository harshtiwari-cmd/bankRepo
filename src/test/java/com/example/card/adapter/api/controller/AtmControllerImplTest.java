package com.example.card.adapter.api.controller;

import com.example.card.adapter.api.services.impl.AtmServiceImpl;
import com.example.card.domain.dto.AtmRequestDto;
import com.example.card.domain.dto.AtmResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AtmController.class)
 class AtmControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AtmServiceImpl atmService;

    private AtmRequestDto requestDto;

    private AtmResponseDto responseDto;

    @BeforeEach
    void setUp() {
         requestDto = AtmRequestDto.builder()
                 .locatorType("ATM")
                .code("ATM001")
                .city("Doha")
                .country("QATAR")
                .timing("08:00 - 20:00")
                .arabicName("aaaa")
                .cashDeposit(true)
                .cashOut(true)
                .chequeDeposit(false)
                .cityInArabic("Doha")
                .contactDetails("1800-ATM")
                .disablePeople(true)
                .fullAddress("Panjagutta Road, Central Mall")
                .fullAddressArb("yyyyy")
                .latitude("17.385044")
                .longitude("78.486671")
                .onlineLocation(true)
                .workingHours("08:00 to 20:00")
                .workingHoursInArb("20:00 to 08:00")
                .build();

        responseDto = AtmResponseDto.builder()
                .locatorType("ATM")
                .code("ATM001")
                .city("Doha")
                .country("QATAR")
                .timing("08:00 - 20:00")
                .arabicName("aaaa")
                .cashDeposit(true)
                .cashOut(true)
                .chequeDeposit(false)
                .cityInArabic("Doha")
                .contactDetails("1800-ATM")
                .disablePeople(true)
                .fullAddress("Panjagutta Road, Central Mall")
                .fullAddressArb("yyyyy")
                .latitude("17.385044")
                .longitude("78.486671")
                .onlineLocation(true)
                .workingHours("08:00 to 20:00")
                .workingHoursInArb("20:00 to 08:00")
                .build();
    }


    @Test
    void testRegisterAtm_shouldReturnSuccess() throws Exception {

        Mockito.when(atmService.registerAtm(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "test-unit")
                        .header("channel", "web")
                        .header("accept-language", "en")
                        .header("serviceId", "service123")
                        .header("screenId", "screen123")
                        .header("moduleId", "module123")
                        .header("subModuleId", "submodule123")
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ATM001"))
                .andExpect(jsonPath("$.city").value("Doha"))
                .andExpect(jsonPath("$.country").value("QATAR"))
                .andExpect(jsonPath("$.cashDeposit").value(true))
                .andExpect(jsonPath("$.cashOut").value(true));

        Mockito.verify(atmService, Mockito.times(1)).registerAtm(requestDto);

    }

    @Test
    void testGetAllAtms_shouldReturnAllAtms() throws Exception {

        Mockito.when(atmService.getAtm()).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "test-unit")
                        .header("channel", "web")
                        .header("accept-language", "en")
                        .header("serviceId", "service123")
                        .header("screenId", "screen123")
                        .header("moduleId", "module123")
                        .header("subModuleId", "submodule123")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].code").value("ATM001"))
                .andExpect(jsonPath("$.data[0].city").value("Doha"))
                .andExpect(jsonPath("$.data[0].country").value("QATAR"))
                .andExpect(jsonPath("$.data[0].cashDeposit").value(true))
                .andExpect(jsonPath("$.data[0].cashOut").value(true));

        Mockito.verify(atmService, Mockito.times(1)).getAtm();


    }

    @Test
    void testGetAllAtms_shouldReturnEmpty() throws Exception {

        Mockito.when(atmService.getAtm()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "test-unit")
                        .header("channel", "web")
                        .header("accept-language", "en")
                        .header("serviceId", "service123")
                        .header("screenId", "screen123")
                        .header("moduleId", "module123")
                        .header("subModuleId", "submodule123")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000404"))
                .andExpect(jsonPath("$.status.description").value("No Data Found"))
                .andExpect(jsonPath("$.data").isEmpty());

        Mockito.verify(atmService, Mockito.times(1)).getAtm();
    }

    @Test
    void testGetAllAtms_shouldThrowException() throws Exception {

        Mockito.when(atmService.getAtm()).thenThrow(new RuntimeException("Database Error"));

        mockMvc.perform(get("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "test-unit")
                        .header("channel", "web")
                        .header("accept-language", "en")
                        .header("serviceId", "service123")
                        .header("screenId", "screen123")
                        .header("moduleId", "module123")
                        .header("subModuleId", "submodule123")
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status.code").value("000500"))
                .andExpect(jsonPath("$.status.description").value("Failed to fetch ATMs"))
                .andExpect(jsonPath("$.data").isEmpty());

        Mockito.verify(atmService, Mockito.times(1)).getAtm();

    }

}
