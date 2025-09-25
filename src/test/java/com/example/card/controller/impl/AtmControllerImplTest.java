package com.example.card.controller.impl;




import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.model.Coordinates;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private AtmRequestDto buildValidRequest() {
        return AtmRequestDto.builder()
                .arabicName("ATM Machine")
                .cashDeposit(true)
                .cashOut(true)
                .chequeDeposit(false)
                .city("Mumbai")
                .cityInArabic("Mumbai")
                .code("ATM123")
                .contactDetails("022-12345678")
                .country("India")
                .disablePeople(false)
                .fullAddress("123 Main Street, Mumbai")
                .fullAddressArb("123 Main Street, Mumbai")
                .latitude("19.0760")
                .longitude("72.8777")
                .onlineLocation(true)
                .timing("24/7")
                .typeLocation("Branch ATM")
                .workingHours("9 AM - 6 PM")
                .workingHoursInArb("9 AM - 6 PM")
                .build();
    }

    @Test
    void testRegisterAtm_success() throws Exception {
        AtmRequestDto requestDto = buildValidRequest();
        requestDto.setCode("ATM123");
        mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ATM123"));
    }

    @Test
    void testRegisterAtm_validationFailure() throws Exception {
        AtmRequestDto invalid = buildValidRequest();
        invalid.setCode(""); // invalid due to @NotBlank

        mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllAtms() throws Exception {
        mockMvc.perform(get("/api/atms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
