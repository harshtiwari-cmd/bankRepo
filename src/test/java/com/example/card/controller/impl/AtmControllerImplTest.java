package com.example.card.controller.impl;




import com.example.card.constrants.dto.AtmRequestDto;
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
                .atmId("AB123456")
                .branchId("402519")
                .siteName("central mall")
                .streetName("panjagutta road")
                .townName("Hyderabad")
                .country("IN")
                .postCode("560038")
                .coordinates(new Coordinates(12.97878, 77.9999))
                .supportedLanguages(List.of("en", "hi","or"))
                .atmServices(List.of("cashWithdrawal", "MiniStateMent"))
                .supportedCurrencies(List.of("INR"))
                .minimumPossibleAmount(100)
                .openTime("08:00")
                .build();
    }

    @Test
    void testRegisterAtm_success() throws Exception {
        AtmRequestDto requestDto = buildValidRequest();

        mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.atmId").value("AB123456"));
    }

    @Test
    void testRegisterAtm_validationFailure() throws Exception {
        AtmRequestDto invalid = buildValidRequest();
        invalid.setAtmId(""); // invalid due to @NotBlank

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
