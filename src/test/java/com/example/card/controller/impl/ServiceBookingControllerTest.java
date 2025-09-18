package com.example.card.controller.impl;

import com.example.card.constrants.dto.ServiceBookingRequestDTO;
import com.example.card.constrants.dto.ServiceBookingResponseDTO;
import com.example.card.controller.ServiceBookingController;
import com.example.card.services.ServiceBookingService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceBookingController.class)
public class ServiceBookingControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceBookingService service;

    private ServiceBookingRequestDTO requestDTO;

    private ServiceBookingResponseDTO responseDTO;

    @BeforeEach
    public void setUp() {

        this.requestDTO = ServiceBookingRequestDTO.builder()
                .serviceName("Open Account")
                .description("Schedule meeting with a financial advisor")
                .isActive(true)
                .build();

        this.responseDTO = ServiceBookingResponseDTO.builder()
                .serviceName("Open Account")
                .description("Schedule meeting with a financial advisor")
                .isActive(true)
                .screenId("screen123")
                .build();
    }

    @Test
    public void shouldCreateServiceBookingSuccessfully() throws Exception {

        Mockito.when(this.service.createService(this.requestDTO, "screen123")).thenReturn(this.responseDTO);

        this.mockMvc.perform(post("/service-booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.requestDTO))
                        .param("screenId", "screen123"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serviceName").value("Open Account"))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.screenId").value("screen123"));
    }

    @Test
    public void shouldReturnBadRequest() throws Exception {

        this.requestDTO.setServiceName(null);

        this.mockMvc.perform(post("/service-booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.requestDTO))
                        .param("screenId", "screen123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.serviceName").value("Service name must not be blank"));
    }

    @Test
    public void shouldReturnAllBookingsForScreenId() throws Exception {

         final ServiceBookingResponseDTO dto1 = ServiceBookingResponseDTO.builder()
                .serviceName("Book Appointment")
                .description("Reserve a time slot with a banker")
                .isActive(true)
                .screenId("screen123")
                .build();

         final ServiceBookingResponseDTO dto2 = ServiceBookingResponseDTO.builder()
                .serviceName("Meet Advisor")
                .description("Schedule meeting with a financial advisor")
                .isActive(true)
                .screenId("screen123")
                .build();

        Mockito.when(this.service.getServiceByScreenId("screen123"))
                .thenReturn(Arrays.asList(this.responseDTO, dto1, dto2));

        this.mockMvc.perform(get("/service-booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("screenId", "screen123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void shouldReturnBadRequestWhenScreenIdIsMissing() throws Exception {
        this.mockMvc.perform(get("/service-booking")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details").value("Missing required parameter: screenId"));

    }

    @Test
    public void shouldReturnNoContentForBlankScreenId() throws Exception {
        this.mockMvc.perform(get("/service-booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("screenId", " "))
                .andExpect(status().isNoContent());
    }
}
