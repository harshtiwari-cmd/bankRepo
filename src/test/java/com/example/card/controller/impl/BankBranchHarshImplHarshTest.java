package com.example.card.controller.impl;

import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import com.example.card.constrants.dto.CoordinatesHarshDTO;
import com.example.card.constrants.dto.HolidayHarshDTO;
import com.example.card.services.BankBranchServiceHarsh;
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
class BankBranchHarshImplHarshTest {

    @Mock
    private BankBranchServiceHarsh bankBranchServiceHarsh;

    @InjectMocks
    private BankBranchImplHarsh bankBranchImplHarsh;

    private BankBranchHarshDTO sampleBankBranchHarshDTO;
    private CreateBankHarshBranchDTO sampleCreateBankHarshBranchDTO;

    @BeforeEach
    void setUp() {
        // Setup sample BankBranchDTO
        sampleBankBranchHarshDTO = new BankBranchHarshDTO();
        sampleBankBranchHarshDTO.setId(1L);
        sampleBankBranchHarshDTO.setBankName("Test Bank");
        sampleBankBranchHarshDTO.setBankBranchName("Main Branch");
        sampleBankBranchHarshDTO.setBranchNumber("BR001");
        sampleBankBranchHarshDTO.setDescription("Main branch description");
        sampleBankBranchHarshDTO.setBankBranchType("Main");
        sampleBankBranchHarshDTO.setCountryName("USA");
        sampleBankBranchHarshDTO.setOpenTime("09:00");
        sampleBankBranchHarshDTO.setCloseTime("17:00");
        sampleBankBranchHarshDTO.setAddress("123 Main St");
        sampleBankBranchHarshDTO.setLocation("New York");
        sampleBankBranchHarshDTO.setContact("555-0123");

        CoordinatesHarshDTO coordinatesHarshDTO = new CoordinatesHarshDTO();
        coordinatesHarshDTO.setLatitude(40.7128);
        coordinatesHarshDTO.setLongitude(-74.0060);
        sampleBankBranchHarshDTO.setCoordinates(coordinatesHarshDTO);

        HolidayHarshDTO holidayHarshDTO = new HolidayHarshDTO();
        holidayHarshDTO.setDate("2024-01-01");
        holidayHarshDTO.setName("New Year");
        holidayHarshDTO.setType("Public");
        sampleBankBranchHarshDTO.setHolidayCalendar(Arrays.asList(holidayHarshDTO));
        sampleBankBranchHarshDTO.setWeeklyHolidays(Arrays.asList("Sunday"));

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
        when(bankBranchServiceHarsh.getAllBranches()).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<BankBranchHarshDTO>> response = bankBranchImplHarsh.getAllBranches();

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchServiceHarsh, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WithBranches_ReturnsOkWithBranches() {
        // Given
        List<BankBranchHarshDTO> branches = Arrays.asList(sampleBankBranchHarshDTO);
        when(bankBranchServiceHarsh.getAllBranches()).thenReturn(branches);

        // When
        ResponseEntity<List<BankBranchHarshDTO>> response = bankBranchImplHarsh.getAllBranches();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleBankBranchHarshDTO, response.getBody().get(0));
        verify(bankBranchServiceHarsh, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WithMultipleBranches_ReturnsOkWithAllBranches() {
        // Given
        BankBranchHarshDTO branch2 = new BankBranchHarshDTO();
        branch2.setId(2L);
        branch2.setBankName("Test Bank 2");
        branch2.setBankBranchName("Branch 2");
        branch2.setBranchNumber("BR002");

        List<BankBranchHarshDTO> branches = Arrays.asList(sampleBankBranchHarshDTO, branch2);
        when(bankBranchServiceHarsh.getAllBranches()).thenReturn(branches);

        // When
        ResponseEntity<List<BankBranchHarshDTO>> response = bankBranchImplHarsh.getAllBranches();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(sampleBankBranchHarshDTO, response.getBody().get(0));
        assertEquals(branch2, response.getBody().get(1));
        verify(bankBranchServiceHarsh, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WhenServiceThrowsException_ReturnsInternalServerError() {
        // Given
        when(bankBranchServiceHarsh.getAllBranches()).thenThrow(new RuntimeException("Database error"));

        // When
        ResponseEntity<List<BankBranchHarshDTO>> response = bankBranchImplHarsh.getAllBranches();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchServiceHarsh, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WhenServiceThrowsIllegalArgumentException_ReturnsInternalServerError() {
        // Given
        when(bankBranchServiceHarsh.getAllBranches()).thenThrow(new IllegalArgumentException("Invalid argument"));

        // When
        ResponseEntity<List<BankBranchHarshDTO>> response = bankBranchImplHarsh.getAllBranches();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchServiceHarsh, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_WhenServiceThrowsNullPointerException_ReturnsInternalServerError() {
        // Given
        when(bankBranchServiceHarsh.getAllBranches()).thenThrow(new NullPointerException("Null pointer"));

        // When
        ResponseEntity<List<BankBranchHarshDTO>> response = bankBranchImplHarsh.getAllBranches();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchServiceHarsh, times(1)).getAllBranches();
    }

    @Test
    void addBranch_WithValidCreateDTO_ReturnsOkWithSavedBranch() {
        // Given
        when(bankBranchServiceHarsh.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchHarshDTO);

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchHarshDTO, response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithNullCreateDTO_ReturnsInternalServerError() {
        // Given
        when(bankBranchServiceHarsh.createBankBranch(null)).thenThrow(new IllegalArgumentException("DTO cannot be null"));

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(null);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(null);
    }

    @Test
    void addBranch_WithCreateDTOMissingRequiredFields_ReturnsInternalServerError() {
        // Given
        CreateBankHarshBranchDTO incompleteDTO = new CreateBankHarshBranchDTO();
        incompleteDTO.setBankName("Test Bank");
        // Missing other required fields
        when(bankBranchServiceHarsh.createBankBranch(incompleteDTO))
                .thenThrow(new IllegalArgumentException("Required fields are missing"));

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(incompleteDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(incompleteDTO);
    }

    @Test
    void addBranch_WhenServiceThrowsRuntimeException_ReturnsInternalServerError() {
        // Given
        when(bankBranchServiceHarsh.createBankBranch(sampleCreateBankHarshBranchDTO))
                .thenThrow(new RuntimeException("Database connection error"));

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WhenServiceThrowsDataAccessException_ReturnsInternalServerError() {
        // Given
        when(bankBranchServiceHarsh.createBankBranch(sampleCreateBankHarshBranchDTO))
                .thenThrow(new RuntimeException("Data access exception"));

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingNullCoordinates_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setCoordinates(null);
        sampleBankBranchHarshDTO.setCoordinates(null);
        when(bankBranchServiceHarsh.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchHarshDTO);

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchHarshDTO, response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingNullHolidayCalendar_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setHolidayCalendar(null);
        sampleBankBranchHarshDTO.setHolidayCalendar(null);
        when(bankBranchServiceHarsh.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchHarshDTO);

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchHarshDTO, response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingEmptyHolidayCalendar_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setHolidayCalendar(new ArrayList<>());
        sampleBankBranchHarshDTO.setHolidayCalendar(new ArrayList<>());
        when(bankBranchServiceHarsh.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchHarshDTO);

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchHarshDTO, response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingNullWeeklyHolidays_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setWeeklyHolidays(null);
        sampleBankBranchHarshDTO.setWeeklyHolidays(null);
        when(bankBranchServiceHarsh.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchHarshDTO);

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchHarshDTO, response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }

    @Test
    void addBranch_WithCreateDTOHavingEmptyWeeklyHolidays_ReturnsOk() {
        // Given
        sampleCreateBankHarshBranchDTO.setWeeklyHolidays(new ArrayList<>());
        sampleBankBranchHarshDTO.setWeeklyHolidays(new ArrayList<>());
        when(bankBranchServiceHarsh.createBankBranch(sampleCreateBankHarshBranchDTO)).thenReturn(sampleBankBranchHarshDTO);

        // When
        ResponseEntity<BankBranchHarshDTO> response = bankBranchImplHarsh.addBranch(sampleCreateBankHarshBranchDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleBankBranchHarshDTO, response.getBody());
        verify(bankBranchServiceHarsh, times(1)).createBankBranch(sampleCreateBankHarshBranchDTO);
    }
}
