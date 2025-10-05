package com.example.card.controller.impl;

import com.example.card.adapter.api.controller.LocateUs;
import com.example.card.adapter.api.services.impl.AtmServiceImpl;
import com.example.card.adapter.api.services.impl.BankBranchServiceImpl;
import com.example.card.adapter.api.services.impl.KioskServiceImpl;
import com.example.card.domain.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocateUs.class)
class LocateUsTest {

    @MockitoBean
    private AtmServiceImpl atmService;

    @MockitoBean
    private BankBranchServiceImpl branchService;

    @MockitoBean
    private KioskServiceImpl kioskService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BankBranchDTO branchDto;
    private AtmResponseDto atmDto;
    private KioskResponseDTO kioskDto;

    @BeforeEach
    void setUp() {

        branchDto = new BankBranchDTO();
        branchDto.setId(1L);
        branchDto.setBankName("Test Bank");
        branchDto.setBankBranchName("Main Branch");
        branchDto.setBranchNumber("B001");
        branchDto.setDescription("Head office branch");
        branchDto.setBankBranchType("URBAN");
        branchDto.setCountryName("India");
        branchDto.setOpenTime("09:00");
        branchDto.setCloseTime("17:00");
        branchDto.setAddress("MG Road, Bangalore");
        branchDto.setLocation("Bangalore");
        branchDto.setContact("+91-9876543210");
        branchDto.setWeeklyHolidays(List.of("Sunday"));
        branchDto.setStatus("ACTIVE");

        CoordinatesHarshDTO coordsBranch = new CoordinatesHarshDTO();
        coordsBranch.setLatitude(12.9716);
        coordsBranch.setLongitude(77.5946);
        branchDto.setCoordinates(coordsBranch);

        HolidayHarshDTO holiday = new HolidayHarshDTO();
        holiday.setDate("2025-01-26");
        holiday.setName("Republic Day");
        holiday.setType("National");
        branchDto.setHolidayCalendar(List.of(holiday));

        atmDto = AtmResponseDto.builder()
                .id(101L)
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

        kioskDto = new KioskResponseDTO();
        kioskDto.setKioskId("K001");
        kioskDto.setBranchId("B001");
        kioskDto.setName("Info Kiosk");
        kioskDto.setDescription("Customer support kiosk");

        LocationDTO kioskLocation = new LocationDTO();
        kioskLocation.setStreetName("MG Road");
        kioskLocation.setTownName("Bangalore");
        kioskLocation.setCountry("India");
        kioskLocation.setPostCode("560001");
        kioskDto.setLocation(kioskLocation);

        kioskDto.setCoordinates(CoordinatesDTO.builder().latitude(12.9716).longitude(77.5946).build());
        kioskDto.setKioskServices(List.of("Cash Deposit", "Balance Enquiry"));
        kioskDto.setOpenTime("09:00");
        kioskDto.setCloseTime("21:00");

        HolidayCalendarDTO kioskHoliday = new HolidayCalendarDTO();
        kioskHoliday.setId(100L);
        kioskHoliday.setDate("2025-08-15");
        kioskHoliday.setName("Independence Day");
        kioskDto.setHolidayCalendar(List.of(kioskHoliday));
        kioskDto.setWeeklyHolidays(List.of("Sunday"));
    }

    @Test
    void getService_ShouldReturnSuccess_WhenDataExists() throws Exception {
        when(branchService.getAllBranchesWithStatus()).thenReturn(List.of(branchDto));
        when(atmService.getAtm()).thenReturn(List.of(atmDto));
        when(kioskService.getKiosk()).thenReturn(List.of(kioskDto));

        mockMvc.perform(
                get("/locate-us")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("lang", "English")
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
                .andExpect(jsonPath("$.data.length()").value(3));

    }

    @Test
    void getService_ShouldReturnNoData_WhenAllEmpty() throws Exception {
        when(branchService.getAllBranchesWithStatus()).thenReturn(Collections.emptyList());
        when(atmService.getAtm()).thenReturn(Collections.emptyList());
        when(kioskService.getKiosk()).thenReturn(Collections.emptyList());

        mockMvc.perform(
                        get("/locate-us")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("unit", "PRD")
                                .header("channel", "MB")
                                .header("lang", "English")
                                .header("serviceId", "LOGIN")
                                .header("screenId", "SC_01")
                                .header("moduleId", "MI_01")
                                .header("subModuleId", "SMI_01")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000404"))
                .andExpect(jsonPath("$.status.description").value("No Data Found"))
                .andExpect(jsonPath("$.data.length()").value(0));

    }

    @Test
    void getService_ShouldReturnInternalServerError_WhenExceptionOccurs() throws Exception {

        when(branchService.getAllBranchesWithStatus()).thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(get("/locate-us")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("lang", "English")
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status.code").value("BRANCH_ERROR"))
                .andExpect(jsonPath("$.status.description").value("Failed to fetch branches"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void getService_ShouldReturnSuccess_WhenOnlyBranchesExist() throws Exception {
        when(branchService.getAllBranchesWithStatus()).thenReturn(List.of(branchDto));
        when(atmService.getAtm()).thenReturn(Collections.emptyList());
        when(kioskService.getKiosk()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/locate-us")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("lang", "English")
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].branches").isArray());
    }

    @Test
    void getService_ShouldReturnSuccess_WhenOnlyAtmsExist() throws Exception {
        when(branchService.getAllBranchesWithStatus()).thenReturn(Collections.emptyList());
        when(atmService.getAtm()).thenReturn(List.of(atmDto));
        when(kioskService.getKiosk()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/locate-us")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("lang", "English")
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[1].atms").isArray());
    }

    @Test
    void getService_ShouldReturnSuccess_WhenOnlyKiosksExist() throws Exception {
        when(branchService.getAllBranchesWithStatus()).thenReturn(Collections.emptyList());
        when(atmService.getAtm()).thenReturn(Collections.emptyList());
        when(kioskService.getKiosk()).thenReturn(List.of(kioskDto));

        mockMvc.perform(get("/locate-us")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("lang", "English")
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[2].kiosks").isArray());
    }
}
