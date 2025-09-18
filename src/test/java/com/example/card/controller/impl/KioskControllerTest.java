package com.example.card.controller.impl;


import com.example.card.constrants.dto.*;
import com.example.card.exceptions.KioskAlreadyExistsException;
import com.example.card.services.KioskService;
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
import java.util.Collections;
import java.util.List;

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

    private KioskRequestDTO requestDTO;

    @BeforeEach
    public void setUp() {

        GeoLocationDTO geoLocation = new GeoLocationDTO();
        geoLocation.setLatitude(34.3023F);
        geoLocation.setLongitude(75.8577F);

        LocationDTO location = new LocationDTO();
        location.setStreetName("MG Road");
        location.setTownName("Indore");
        location.setCountry("India");
        location.setPostCode("452001");
        location.setGeoLocation(geoLocation);

        HolidayCalendarDTO holiday1 = new HolidayCalendarDTO();
        holiday1.setDate("10-05-2025");
        holiday1.setName("Saturday");

        KioskRequestDTO kioskRequestDTO = new KioskRequestDTO();
        kioskRequestDTO.setKioskId("K001");
        kioskRequestDTO.setBranchId("B001");
        kioskRequestDTO.setName("Main Kiosk");
        kioskRequestDTO.setDescription("Primary branch kiosk");
        kioskRequestDTO.setLocation(location);
        kioskRequestDTO.setKioskServices(List.of("AccountOpening", "MoneyTransfer"));
        kioskRequestDTO.setOpenTime("09:00");
        kioskRequestDTO.setCloseTime("18:00");
        kioskRequestDTO.setHolidayCalendar(List.of(holiday1));
        kioskRequestDTO.setWeeklyHolidays(List.of("Sunday"));

        requestDTO = kioskRequestDTO;
    }



    @Test
    public void shouldCreateKioskSuccessfully() throws Exception {

        final KioskResponseDTO responseDTO = new KioskResponseDTO();

        responseDTO.setKioskId("K001");
        responseDTO.setBranchId("B001");
        responseDTO.setName("Main Kiosk");
        responseDTO.setDescription("Test kiosk");

        Mockito.when(this.service.createKiosk(any(KioskRequestDTO.class))).thenReturn(responseDTO);


        this.mockMvc.perform(post("/kiosk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.kioskId").value("K001"))
                .andExpect(jsonPath("$.name").value("Main Kiosk"));
    }

    @Test
    public void shouldReturn400_whenKioskIdNotPassed() throws Exception {

        requestDTO.setKioskId(" "); // invalid kioskId
        requestDTO.setBranchId("B001");
        requestDTO.setName("Main Kiosk");

        this.mockMvc.perform(post("/kiosk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.kioskId").value("kiosk id should not be blank"));

    }


    @Test
    public void shouldReturn400_whenServiceIsEmpty() throws Exception {

        requestDTO.setKioskServices(Collections.emptyList());

        mockMvc.perform(post("/kiosk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.kioskServices").value("must not be empty"));
    }

    @Test
    public void testCreateKiosk_emptyServicesList() throws Exception {

        requestDTO.setHolidayCalendar(Collections.emptyList());

        mockMvc.perform(post("/kiosk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.holidayCalendar").value("must not be empty"));
    }

    @Test
    public void shouldThrowKioskIdAlreadyExitException() throws Exception {

        Mockito.when(service.createKiosk(any(KioskRequestDTO.class)))
                .thenThrow(new KioskAlreadyExistsException("Kiosk with Id " + requestDTO.getKioskId() + "already exit"));

        mockMvc.perform(post("/kiosk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());

        Mockito.verify(service, Mockito.times(1)).createKiosk(requestDTO);
    }

    @Test
    public void shouldThrowException() throws Exception {

        Mockito.when(service.createKiosk(any(KioskRequestDTO.class)))
                .thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(post("/kiosk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isInternalServerError());
    }



}
