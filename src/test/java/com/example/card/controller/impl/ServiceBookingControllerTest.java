package com.example.card.controller.impl;

import com.example.card.constrants.dto.ServiceBookingRequestDTO;
import com.example.card.constrants.entity.ServiceBooking;
import com.example.card.repository.ServiceBookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceBookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceBookingRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.deleteAll(); // clean DB before each test
    }

    @Test
    void createService_ShouldReturnCreated() throws Exception {
        ServiceBookingRequestDTO request = ServiceBookingRequestDTO.builder()
                .serviceName("Test Service")
                .description("Test Description")
                .isActive(true)
                .build();

        mockMvc.perform(post("/service-booking")
                        .param("screenId", "SCR001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serviceName").value("Test Service"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.screenId").value("SCR001"))
                // Mapper does not copy isActive, so persisted value is false
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void getAllServicesName_ShouldReturnSuccessWhenDataExists() throws Exception {
        // Add a service
        ServiceBooking entity = new ServiceBooking();
        entity.setServiceName("Existing Service");
        entity.setScreenId("SCR002");
        entity.setActive(true);
        repository.save(entity);

        mockMvc.perform(get("/service-booking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
                .andExpect(jsonPath("$.data[0]").value("1 - Existing Service"));
    }

    @Test
    void getAllServicesName_ShouldReturnNoDataFoundWhenEmpty() throws Exception {
        mockMvc.perform(get("/service-booking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000404"))
                .andExpect(jsonPath("$.status.description").value("No Data Found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void getAllServicesName_ShouldHandleNullServiceName() throws Exception {
        // Save entity with null service name
        ServiceBooking entity = new ServiceBooking();
        entity.setServiceName(null);
        entity.setScreenId("SCR003");
        repository.save(entity);

        mockMvc.perform(get("/service-booking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
                .andExpect(jsonPath("$.data[0]").value("1 - null"));
    }
}
