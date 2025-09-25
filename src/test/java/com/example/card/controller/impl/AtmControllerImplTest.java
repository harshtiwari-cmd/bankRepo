package com.example.card.controller.impl;



import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.model.Coordinates;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AtmControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Build a valid request DTO based on your updated entity structure
    private AtmRequestDto buildValidRequest() {
        return AtmRequestDto.builder()
                .code("ATM001")
                .city("Hyderabad")
                .country("IN")
                .timing("08:00 - 20:00")
                .arabicName("aaaaa")
                .cashDeposit(true)
                .cashOut(true)
                .chequeDeposit(false)
                .cityInArabic("bbbb")
                .contactDetails("1800-ATM")
                .disablePeople(true)
                .fullAddress("Panjagutta Road, Central Mall")
                .fullAddressArb("yyyyyyy")
                .latitude("17.385044")
                .longitude("78.486671")
                .onlineLocation(true)
                .typeLocation("Indoor")
                .workingHours("08:00 to 20:00")
                .workingHoursInArb("من 08:00 إلى 20:00")
                .build();
    }

    @Test
    @DisplayName("POST /api/atms - Success - Register ATM")
    void testRegisterAtm_success() throws Exception {
        AtmRequestDto requestDto = buildValidRequest();

        mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ATM001"))
                .andExpect(jsonPath("$.city").value("Hyderabad"))
                .andExpect(jsonPath("$.country").value("IN"))
                .andExpect(jsonPath("$.timing").value("08:00 - 20:00"))
                .andExpect(jsonPath("$.cashDeposit").value(true))
                .andExpect(jsonPath("$.cashOut").value(true));
    }

    @Test
    @DisplayName("POST /api/atms - Validation Failure - Missing required fields")
    void testRegisterAtm_validationFailure() throws Exception {
        AtmRequestDto invalid = buildValidRequest();
        invalid.setCode(""); // assuming @NotBlank on code

        mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("GET /api/atms - Success - Retrieve list of ATMs")
    void testGetAllAtms_success() throws Exception {
        mockMvc.perform(get("/api/atms"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
}
