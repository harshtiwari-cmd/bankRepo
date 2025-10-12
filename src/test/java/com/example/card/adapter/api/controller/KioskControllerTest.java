package com.example.card.adapter.api.controller;


import com.example.card.domain.dto.HolidayCalendarDTO;
import com.example.card.domain.dto.KioskRequestDTO;
import com.example.card.domain.dto.KioskResponseDTO;
import com.example.card.adapter.api.services.KioskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KioskController.class)
public class KioskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private KioskService service;

    @Test
    public void testCreateKiosk_success() throws Exception {

        KioskRequestDTO requestDTO = new KioskRequestDTO();

        requestDTO.setKioskId("K001");
        requestDTO.setBranchId("B001");
        requestDTO.setName("Main Kiosk");
        requestDTO.setKioskServices(Arrays.asList("AccountOpening", "MoneyTransfer"));

        ArrayList<HolidayCalendarDTO> list = new ArrayList<>();
        HolidayCalendarDTO calendarDTO = new HolidayCalendarDTO();
        calendarDTO.setDate("10-05-2025");
        calendarDTO.setName("Saturday");

        requestDTO.setHolidayCalendar(list);

        KioskResponseDTO responseDTO = new KioskResponseDTO();

        responseDTO.setKioskId("K001");
        responseDTO.setBranchId("B001");
        responseDTO.setName("Main Kiosk");
        responseDTO.setDescription("Test kiosk");

        Mockito.when(service.createKiosk(any(KioskRequestDTO.class))).thenReturn(responseDTO);


        mockMvc.perform(post("/kiosk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("lang", "English")
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.kioskId").value("K001"))
                .andExpect(jsonPath("$.name").value("Main Kiosk"));
    }

    @Test
    public void testCreateKiosk_failed() throws Exception {

        KioskRequestDTO requestDTO = new KioskRequestDTO();

        requestDTO.setKioskId("K001");
        requestDTO.setBranchId("B001");
        requestDTO.setName("Main Kiosk");

        KioskResponseDTO responseDTO = new KioskResponseDTO();

        responseDTO.setKioskId("K001");
        responseDTO.setBranchId("B001");
        responseDTO.setName("Main Kiosk");

        mockMvc.perform(post("/kiosk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());

    }


}
