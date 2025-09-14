package com.example.card.controller.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.services.AtmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AtmControllerImplTest {


    private MockMvc mockMvc;

    @Mock
    private AtmService iAtmService;

    @InjectMocks
    private AtmControllerImpl iAtmController;

    private final ObjectMapper objectMapper=new ObjectMapper();

    @Test
    void testRegisterAtm () throws Exception{
        mockMvc= MockMvcBuilders.standaloneSetup(iAtmController).build();

        AtmRequestDto dto= AtmRequestDto.builder()
                .atmId("AB123456")
                .branchId("402519")
                .siteName("central mall")
                .townName("Hyderabad")
                .country("IN")
                .openTime("08:00")
                .build();

        when(iAtmService.registerAtm(dto)).thenReturn(null);

        mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }



}
