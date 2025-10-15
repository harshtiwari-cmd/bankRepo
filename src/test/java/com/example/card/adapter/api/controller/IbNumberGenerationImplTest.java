package com.example.card.adapter.api.controller;

import com.example.card.adapter.api.services.ReferenceNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IbNumberGenerationController.class)
class IbNumberGenerationImplTest {

    @MockitoBean
    private ReferenceNumberService referenceNumberService;

    @Autowired
    private MockMvc mockMvc;


    private String channelName;
    private String generatedRef;

    @BeforeEach
    void setUp() {
        channelName = "RIB";
        generatedRef = "RIB16092025000001";
    }

    @Test
    void generate_withValidChannel_returnsReference() throws Exception {

        when(referenceNumberService.generateReferenceNumber(channelName)).thenReturn("RIB16092025000001");

        mockMvc.perform(get("/reference/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("accept-language", "English")
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01")
                        .param("channelName", "RIB")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.referenceNumber").value("RIB16092025000001"))
                .andExpect(jsonPath("$.message").value("Reference generated successfully"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(referenceNumberService, times(1)).generateReferenceNumber(channelName);
    }


    @Test
    void generate_withNullChannel_throwsBadRequest() throws Exception {
        mockMvc.perform(get("/reference/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("accept-language", "English")
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Missing Request Parameter"))
                .andExpect(jsonPath("$.details").value("Missing required parameter: channelName"));
    }


    @Test
    void generate_whenServiceThrowsException_propagatesException() throws Exception {
        when(referenceNumberService.generateReferenceNumber(channelName))
                .thenThrow(new RuntimeException("Unexpected DB error"));

        mockMvc.perform(get("/reference/generate")
                        .param("channelName", channelName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("accept-language", "English")
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.details").value("Unexpected DB error"));
    }

}
