package com.example.card.controller.impl;

import com.example.card.constrants.dto.*;
import com.example.card.controller.LocateUs;
import com.example.card.services.impl.AtmServiceImpl;
import com.example.card.services.impl.BankBranchServiceImpl;
import com.example.card.services.impl.KioskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocateUsTest {

    private AtmServiceImpl atmService;
    private BankBranchServiceImpl branchService;
    private KioskServiceImpl kioskService;

    private LocateUs locateUs;

    private BankBranchDTO branchDto;
    private AtmResponseDto atmDto;
    private KioskResponseDTO kioskDto;

    @BeforeEach
    void setUp() {
        atmService = mock(AtmServiceImpl.class);
        branchService = mock(BankBranchServiceImpl.class);
        kioskService = mock(KioskServiceImpl.class);

        locateUs = new LocateUs(atmService, branchService, kioskService);

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
                .id(10L)
                .atmId("A001")
                .branchId("B001")
                .siteName("Central ATM Site")
                .townName("Bangalore")
                .country("India")
                .openTime("24x7")
                .coordinates(CoordinatesDTO.builder().latitude(12.9716).longitude(77.5946).build())
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
    void getService_ShouldReturnSuccess_WhenDataExists() {
        when(branchService.getAllBranchesWithStatus()).thenReturn(List.of(branchDto));
        when(atmService.getAtm()).thenReturn(List.of(atmDto));
        when(kioskService.getKiosk()).thenReturn(List.of(kioskDto));

        ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> response = locateUs.getService();

        assertEquals(200, response.getStatusCodeValue());
        GenericResponse<List<Map<String, List<?>>>> body = response.getBody();
        assertNotNull(body);
        assertEquals("000000", body.getStatus().getCode());
        assertEquals("SUCCESS", body.getStatus().getDescription());
        assertEquals(3, body.getData().size());
    }

    @Test
    void getService_ShouldReturnNoData_WhenAllEmpty() {
        when(branchService.getAllBranchesWithStatus()).thenReturn(Collections.emptyList());
        when(atmService.getAtm()).thenReturn(Collections.emptyList());
        when(kioskService.getKiosk()).thenReturn(Collections.emptyList());

        ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> response = locateUs.getService();

        assertEquals(200, response.getStatusCodeValue());
        GenericResponse<List<Map<String, List<?>>>> body = response.getBody();
        assertNotNull(body);
        assertEquals("000404", body.getStatus().getCode());
        assertEquals("No Data Found", body.getStatus().getDescription());
        assertTrue(body.getData().isEmpty());
    }

    @Test
    void getService_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        when(branchService.getAllBranchesWithStatus()).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> response = locateUs.getService();

        assertEquals(500, response.getStatusCodeValue());
        GenericResponse<List<Map<String, List<?>>>> body = response.getBody();
        assertNotNull(body);
        assertEquals("G-00001", body.getStatus().getCode());
        assertEquals("Internal Server ERROR", body.getStatus().getDescription());
        assertNull(body.getData());
    }

    @Test
    void getService_ShouldReturnSuccess_WhenOnlyBranchesExist() {
        when(branchService.getAllBranchesWithStatus()).thenReturn(List.of(branchDto));
        when(atmService.getAtm()).thenReturn(Collections.emptyList());
        when(kioskService.getKiosk()).thenReturn(Collections.emptyList());

        ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> response = locateUs.getService();

        assertEquals(200, response.getStatusCodeValue());
        GenericResponse<List<Map<String, List<?>>>> body = response.getBody();
        assertNotNull(body);
        assertEquals("000000", body.getStatus().getCode());
    }

    @Test
    void getService_ShouldReturnSuccess_WhenOnlyAtmsExist() {
        when(branchService.getAllBranchesWithStatus()).thenReturn(Collections.emptyList());
        when(atmService.getAtm()).thenReturn(List.of(atmDto));
        when(kioskService.getKiosk()).thenReturn(Collections.emptyList());

        ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> response = locateUs.getService();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("000000", response.getBody().getStatus().getCode());
    }

    @Test
    void getService_ShouldReturnSuccess_WhenOnlyKiosksExist() {
        when(branchService.getAllBranchesWithStatus()).thenReturn(Collections.emptyList());
        when(atmService.getAtm()).thenReturn(Collections.emptyList());
        when(kioskService.getKiosk()).thenReturn(List.of(kioskDto));

        ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> response = locateUs.getService();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("000000", response.getBody().getStatus().getCode());
    }
}
