package com.example.card.controller.impl;

import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import com.example.card.constrants.dto.CoordinatesHarshDTO;
import com.example.card.constrants.dto.HolidayHarshDTO;
import com.example.card.services.BankBranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankBranchImplTest {

    @Mock
    private BankBranchService bankBranchService;

    @InjectMocks
    private BankBranchImpl bankBranchImpl;

    private BankBranchDTO sampleBankBranchDTO;
    private CreateBankHarshBranchDTO sampleCreateBankHarshBranchDTO;

    @BeforeEach
    void setUp() {
        // Setup sample BankBranchDTO
        sampleBankBranchDTO = new BankBranchDTO();
        sampleBankBranchDTO.setId(1L);
        sampleBankBranchDTO.setBankName("Test Bank");
        sampleBankBranchDTO.setBankBranchName("Main Branch");
        sampleBankBranchDTO.setBranchNumber("BR001");
        sampleBankBranchDTO.setDescription("Main branch description");
        sampleBankBranchDTO.setBankBranchType("Main");
        sampleBankBranchDTO.setCountryName("USA");
        sampleBankBranchDTO.setOpenTime("09:00");
        sampleBankBranchDTO.setCloseTime("17:00");
        sampleBankBranchDTO.setAddress("123 Main St");
        sampleBankBranchDTO.setLocation("New York");
        sampleBankBranchDTO.setContact("555-0123");

        CoordinatesHarshDTO coordinatesHarshDTO = new CoordinatesHarshDTO();
        coordinatesHarshDTO.setLatitude(40.7128);
        coordinatesHarshDTO.setLongitude(-74.0060);
        sampleBankBranchDTO.setCoordinates(coordinatesHarshDTO);

        HolidayHarshDTO holidayHarshDTO = new HolidayHarshDTO();
        holidayHarshDTO.setDate("2024-01-01");
        holidayHarshDTO.setName("New Year");
        holidayHarshDTO.setType("Public");
        sampleBankBranchDTO.setHolidayCalendar(Arrays.asList(holidayHarshDTO));
        sampleBankBranchDTO.setWeeklyHolidays(Arrays.asList("Sunday"));

        // Setup sample CreateBankBranchDTO
        sampleCreateBankHarshBranchDTO = new CreateBankHarshBranchDTO();
        sampleCreateBankHarshBranchDTO.setBankName("Test Bank");
        sampleCreateBankHarshBranchDTO.setBankBranchName("Main Branch");
        sampleCreateBankHarshBranchDTO.setBranchNumber("BR001");
        sampleCreateBankHarshBranchDTO.setDescription("Main branch description");
        sampleCreateBankHarshBranchDTO.setBankBranchType("Main");
        sampleCreateBankHarshBranchDTO.setCountryName("USA");
        sampleCreateBankHarshBranchDTO.setOpenTime("09:00");
        sampleCreateBankHarshBranchDTO.setCloseTime("17:00");
        sampleCreateBankHarshBranchDTO.setAddress("123 Main St");
        sampleCreateBankHarshBranchDTO.setLocation("New York");
        sampleCreateBankHarshBranchDTO.setContact("555-0123");

        CoordinatesHarshDTO createCoordinatesHarshDTO = new CoordinatesHarshDTO();
        createCoordinatesHarshDTO.setLatitude(40.7128);
        createCoordinatesHarshDTO.setLongitude(-74.0060);
        sampleCreateBankHarshBranchDTO.setCoordinates(createCoordinatesHarshDTO);

        HolidayHarshDTO createHolidayHarshDTO = new HolidayHarshDTO();
        createHolidayHarshDTO.setDate("2024-01-01");
        createHolidayHarshDTO.setName("New Year");
        createHolidayHarshDTO.setType("Public");
        sampleCreateBankHarshBranchDTO.setHolidayCalendar(Arrays.asList(createHolidayHarshDTO));
        sampleCreateBankHarshBranchDTO.setWeeklyHolidays(Arrays.asList("Sunday"));
    }

    @Test
    void getAllBranches_WithEmptyList_ReturnsNoContent() {
        // Given
        when(bankBranchService.getAllBranches()).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<BankBranchDTO>> response = bankBranchImpl.getAllBranches();

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchService, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WithBranches_ReturnsOkWithBranches() {
        // Given
        List<BankBranchDTO> branches = Arrays.asList(sampleBankBranchDTO);
        when(bankBranchService.getAllBranches()).thenReturn(branches);

        // When
        ResponseEntity<List<BankBranchDTO>> response = bankBranchImpl.getAllBranches();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleBankBranchDTO, response.getBody().get(0));
        verify(bankBranchService, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WithMultipleBranches_ReturnsOkWithAllBranches() {
        // Given
        BankBranchDTO branch2 = new BankBranchDTO();
        branch2.setId(2L);
        branch2.setBankName("Test Bank 2");
        branch2.setBankBranchName("Branch 2");
        branch2.setBranchNumber("BR002");

        List<BankBranchDTO> branches = Arrays.asList(sampleBankBranchDTO, branch2);
        when(bankBranchService.getAllBranches()).thenReturn(branches);

        // When
        ResponseEntity<List<BankBranchDTO>> response = bankBranchImpl.getAllBranches();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(sampleBankBranchDTO, response.getBody().get(0));
        assertEquals(branch2, response.getBody().get(1));
        verify(bankBranchService, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WhenServiceThrowsException_ReturnsInternalServerError() {
        // Given
        when(bankBranchService.getAllBranches()).thenThrow(new RuntimeException("Database error"));

        // When
        ResponseEntity<List<BankBranchDTO>> response = bankBranchImpl.getAllBranches();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchService, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WhenServiceThrowsIllegalArgumentException_ReturnsInternalServerError() {
        // Given
        when(bankBranchService.getAllBranches()).thenThrow(new IllegalArgumentException("Invalid argument"));

        // When
        ResponseEntity<List<BankBranchDTO>> response = bankBranchImpl.getAllBranches();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchService, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WhenServiceThrowsNullPointerException_ReturnsInternalServerError() {
        // Given
        when(bankBranchService.getAllBranches()).thenThrow(new NullPointerException("Null pointer"));

        // When
        ResponseEntity<List<BankBranchDTO>> response = bankBranchImpl.getAllBranches();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchService, times(1)).getAllBranches();
    }

    @Test
    void addBranch_WithValidCreateDTO_ReturnsOkWithSavedBranch() {
        // Given
        when(bankBranchService.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchDTO);

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchDTO, response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithNullCreateDTO_ReturnsInternalServerError() {
        // Given
        when(bankBranchService.createBankBranch(null)).thenThrow(new IllegalArgumentException("DTO cannot be null"));

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(null);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(null);
    }

    @Test
    void addBranch_WithCreateDTOMissingRequiredFields_ReturnsInternalServerError() {
        // Given
        CreateBankHarshBranchDTO incompleteDTO = new CreateBankHarshBranchDTO();
        incompleteDTO.setBankName("Test Bank");
        // Missing other required fields
        when(bankBranchService.createBankBranch(incompleteDTO))
                .thenThrow(new IllegalArgumentException("Required fields are missing"));

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(incompleteDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(incompleteDTO);
    }

    @Test
    void addBranch_WhenServiceThrowsRuntimeException_ReturnsInternalServerError() {
        // Given
        when(bankBranchService.createBankBranch(sampleCreateBankHarshBranchDTO))
                .thenThrow(new RuntimeException("Database connection error"));

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WhenServiceThrowsDataAccessException_ReturnsInternalServerError() {
        // Given
        when(bankBranchService.createBankBranch(sampleCreateBankHarshBranchDTO))
                .thenThrow(new RuntimeException("Data access exception"));

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingNullCoordinates_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setCoordinates(null);
        sampleBankBranchDTO.setCoordinates(null);
        when(bankBranchService.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchDTO);

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchDTO, response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingNullHolidayCalendar_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setHolidayCalendar(null);
        sampleBankBranchDTO.setHolidayCalendar(null);
        when(bankBranchService.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchDTO);

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchDTO, response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingEmptyHolidayCalendar_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setHolidayCalendar(new ArrayList<>());
        sampleBankBranchDTO.setHolidayCalendar(new ArrayList<>());
        when(bankBranchService.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchDTO);

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchDTO, response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingNullWeeklyHolidays_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setWeeklyHolidays(null);
        sampleBankBranchDTO.setWeeklyHolidays(null);
        when(bankBranchService.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchDTO);

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchDTO, response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingEmptyWeeklyHolidays_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setWeeklyHolidays(new ArrayList<>());
        sampleBankBranchDTO.setWeeklyHolidays(new ArrayList<>());
        when(bankBranchService.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchDTO);

        // When
        ResponseEntity<BankBranchDTO> response = bankBranchImpl.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchDTO, response.getBody());
        verify(bankBranchService, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }
}
