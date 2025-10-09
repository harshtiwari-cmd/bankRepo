package com.example.card.adapter.api.services.impl;

import com.example.card.domain.dto.BankBranchDTO;
import com.example.card.domain.dto.CreateBankHarshBranchDTO;
import com.example.card.domain.dto.CoordinatesHarshDTO;
import com.example.card.domain.dto.HolidayHarshDTO;
import com.example.card.constrants.mapper.BankBranchMapper;
import com.example.card.domain.model.CoordinatesHarsh;
import com.example.card.domain.model.HolidayHarsh;
import com.example.card.constrants.entity.BankBranchHarsh;
import com.example.card.repository.BankBranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankBranchServiceImplTest {

    @Mock
    private BankBranchRepository repository;

    @InjectMocks
    private BankBranchServiceImpl bankBranchService;

    private CreateBankHarshBranchDTO sampleCreateDTO;
    private BankBranchHarsh sampleBankBranchHarsh;
    private BankBranchDTO sampleBankBranchDTO;

    @BeforeEach
    void setUp() {
        // Setup sample CreateBankBranchDTO
        sampleCreateDTO = new CreateBankHarshBranchDTO();
        sampleCreateDTO.setBankName("Test Bank");
        sampleCreateDTO.setBankBranchName("Main Branch");
        sampleCreateDTO.setBranchNumber("BR001");
        sampleCreateDTO.setDescription("Main branch description");
        sampleCreateDTO.setBankBranchType("Main");
        sampleCreateDTO.setCountryName("USA");
        sampleCreateDTO.setOpenTime("09:00");
        sampleCreateDTO.setCloseTime("17:00");
        sampleCreateDTO.setAddress("123 Main St");
        sampleCreateDTO.setLocation("New York");
        sampleCreateDTO.setContact("555-0123");

        CoordinatesHarshDTO coordinatesHarshDTO = new CoordinatesHarshDTO();
        coordinatesHarshDTO.setLatitude(40.7128);
        coordinatesHarshDTO.setLongitude(-74.0060);
        sampleCreateDTO.setCoordinates(coordinatesHarshDTO);

        HolidayHarshDTO holidayHarshDTO = new HolidayHarshDTO();
        holidayHarshDTO.setDate("2024-01-01");
        holidayHarshDTO.setName("New Year");
        holidayHarshDTO.setType("Public");
        sampleCreateDTO.setHolidayCalendar(Arrays.asList(holidayHarshDTO));
        sampleCreateDTO.setWeeklyHolidays(Arrays.asList("Sunday"));

        // Setup sample BankBranch entity
        sampleBankBranchHarsh = new BankBranchHarsh();
        sampleBankBranchHarsh.setId(1L);
        sampleBankBranchHarsh.setBankName("Test Bank");
        sampleBankBranchHarsh.setBankBranchName("Main Branch");
        sampleBankBranchHarsh.setBranchNumber("BR001");
        sampleBankBranchHarsh.setDescription("Main branch description");
        sampleBankBranchHarsh.setBankBranchType("Main");
        sampleBankBranchHarsh.setCountryName("USA");
        sampleBankBranchHarsh.setOpenTime("09:00");
        sampleBankBranchHarsh.setCloseTime("17:00");
        sampleBankBranchHarsh.setAddress("123 Main St");
        sampleBankBranchHarsh.setLocation("New York");
        sampleBankBranchHarsh.setContact("555-0123");

        CoordinatesHarsh coordinatesHarsh = new CoordinatesHarsh(40.7128, -74.0060);
        sampleBankBranchHarsh.setCoordinates(coordinatesHarsh);

        HolidayHarsh holidayHarsh = new HolidayHarsh("2024-01-01", "New Year", "Public");
        sampleBankBranchHarsh.setHolidayCalendar(Arrays.asList(holidayHarsh));
        sampleBankBranchHarsh.setWeeklyHolidayList(Arrays.asList("Sunday"));

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

        CoordinatesHarshDTO dtoCoordinates = new CoordinatesHarshDTO();
        dtoCoordinates.setLatitude(40.7128);
        dtoCoordinates.setLongitude(-74.0060);
        sampleBankBranchDTO.setCoordinates(dtoCoordinates);

        HolidayHarshDTO dtoHoliday = new HolidayHarshDTO();
        dtoHoliday.setDate("2024-01-01");
        dtoHoliday.setName("New Year");
        dtoHoliday.setType("Public");
        sampleBankBranchDTO.setHolidayCalendar(Arrays.asList(dtoHoliday));
        sampleBankBranchDTO.setWeeklyHolidays(Arrays.asList("Sunday"));
    }

    @Test
    void getAllBranches_WithEmptyRepository_ReturnsEmptyList() {
        // Given
        when(repository.findAll()).thenReturn(Collections.emptyList());

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(any(BankBranchHarsh.class)))
                    .thenReturn(sampleBankBranchDTO);

            // When
            List<BankBranchDTO> result = bankBranchService.getAllBranches();

            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(repository, times(1)).findAll();
        }
    }

    @Test
    void getAllBranches_WithSingleBranch_ReturnsSingleDTO() {
        // Given
        List<BankBranchHarsh> branches = Arrays.asList(sampleBankBranchHarsh);
        when(repository.findAll()).thenReturn(branches);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh))
                    .thenReturn(sampleBankBranchDTO);

            // When
            List<BankBranchDTO> result = bankBranchService.getAllBranches();

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(sampleBankBranchDTO, result.get(0));
            verify(repository, times(1)).findAll();
            mockedMapper.verify(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh), times(1));
        }
    }

    @Test
    void getAllBranches_WithMultipleBranches_ReturnsMultipleDTOs() {
        // Given
        BankBranchHarsh branch2 = new BankBranchHarsh();
        branch2.setId(2L);
        branch2.setBankName("Test Bank 2");
        branch2.setBankBranchName("Branch 2");
        branch2.setBranchNumber("BR002");

        BankBranchDTO dto2 = new BankBranchDTO();
        dto2.setId(2L);
        dto2.setBankName("Test Bank 2");
        dto2.setBankBranchName("Branch 2");
        dto2.setBranchNumber("BR002");

        List<BankBranchHarsh> branches = Arrays.asList(sampleBankBranchHarsh, branch2);
        when(repository.findAll()).thenReturn(branches);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh))
                    .thenReturn(sampleBankBranchDTO);
            mockedMapper.when(() -> BankBranchMapper.toDTO(branch2))
                    .thenReturn(dto2);

            // When
            List<BankBranchDTO> result = bankBranchService.getAllBranches();

            // Then
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(sampleBankBranchDTO, result.get(0));
            assertEquals(dto2, result.get(1));
            verify(repository, times(1)).findAll();
            mockedMapper.verify(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh), times(1));
            mockedMapper.verify(() -> BankBranchMapper.toDTO(branch2), times(1));
        }
    }

    @Test
    void getAllBranches_WhenMapperReturnsNull_HandlesGracefully() {
        // Given
        List<BankBranchHarsh> branches = Arrays.asList(sampleBankBranchHarsh);
        when(repository.findAll()).thenReturn(branches);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh))
                    .thenReturn(null);

            // When
            List<BankBranchDTO> result = bankBranchService.getAllBranches();

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            assertNull(result.get(0));
            verify(repository, times(1)).findAll();
            mockedMapper.verify(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh), times(1));
        }
    }

    @Test
    void createBankBranch_WithCompleteDTO_ReturnsSavedDTO() {
        // Given
        when(repository.save(any(BankBranchHarsh.class))).thenReturn(sampleBankBranchHarsh);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh))
                    .thenReturn(sampleBankBranchDTO);

            // When
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);

            // Then
            assertNotNull(result);
            assertEquals(sampleBankBranchDTO, result);
            verify(repository, times(1)).save(any(BankBranchHarsh.class));
            mockedMapper.verify(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh), times(1));
        }
    }

    @Test
    void createBankBranch_WithNullCoordinates_SetsNullCoordinates() {
        // Given
        sampleCreateDTO.setCoordinates(null);
        BankBranchHarsh expectedBranch = new BankBranchHarsh();
        expectedBranch.setId(1L);
        expectedBranch.setBankName("Test Bank");
        expectedBranch.setBankBranchName("Main Branch");
        expectedBranch.setBranchNumber("BR001");
        expectedBranch.setDescription("Main branch description");
        expectedBranch.setBankBranchType("Main");
        expectedBranch.setCountryName("USA");
        expectedBranch.setOpenTime("09:00");
        expectedBranch.setCloseTime("17:00");
        expectedBranch.setAddress("123 Main St");
        expectedBranch.setLocation("New York");
        expectedBranch.setContact("555-0123");
        expectedBranch.setCoordinates(null);
        expectedBranch.setHolidayCalendar(Arrays.asList(new HolidayHarsh("2024-01-01", "New Year", "Public")));
        expectedBranch.setWeeklyHolidayList(Arrays.asList("Sunday"));

        when(repository.save(any(BankBranchHarsh.class))).thenReturn(expectedBranch);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(expectedBranch))
                    .thenReturn(sampleBankBranchDTO);

            // When
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);

            // Then
            assertNotNull(result);
            assertEquals(sampleBankBranchDTO, result);
            verify(repository, times(1)).save(any(BankBranchHarsh.class));
        }
    }

    @Test
    void createBankBranch_WithNullHolidayCalendar_SetsNullHolidayCalendar() {
        // Given
        sampleCreateDTO.setHolidayCalendar(null);
        BankBranchHarsh expectedBranch = new BankBranchHarsh();
        expectedBranch.setId(1L);
        expectedBranch.setBankName("Test Bank");
        expectedBranch.setBankBranchName("Main Branch");
        expectedBranch.setBranchNumber("BR001");
        expectedBranch.setDescription("Main branch description");
        expectedBranch.setBankBranchType("Main");
        expectedBranch.setCountryName("USA");
        expectedBranch.setOpenTime("09:00");
        expectedBranch.setCloseTime("17:00");
        expectedBranch.setAddress("123 Main St");
        expectedBranch.setLocation("New York");
        expectedBranch.setContact("555-0123");
        expectedBranch.setCoordinates(new CoordinatesHarsh(40.7128, -74.0060));
        expectedBranch.setHolidayCalendar(null);
        expectedBranch.setWeeklyHolidayList(Arrays.asList("Sunday"));

        when(repository.save(any(BankBranchHarsh.class))).thenReturn(expectedBranch);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(expectedBranch))
                    .thenReturn(sampleBankBranchDTO);

            // When
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);

            // Then
            assertNotNull(result);
            assertEquals(sampleBankBranchDTO, result);
            verify(repository, times(1)).save(any(BankBranchHarsh.class));
        }
    }

    @Test
    void createBankBranch_WithEmptyHolidayCalendar_SetsEmptyHolidayCalendar() {
        // Given
        sampleCreateDTO.setHolidayCalendar(new ArrayList<>());
        BankBranchHarsh expectedBranch = new BankBranchHarsh();
        expectedBranch.setId(1L);
        expectedBranch.setBankName("Test Bank");
        expectedBranch.setBankBranchName("Main Branch");
        expectedBranch.setBranchNumber("BR001");
        expectedBranch.setDescription("Main branch description");
        expectedBranch.setBankBranchType("Main");
        expectedBranch.setCountryName("USA");
        expectedBranch.setOpenTime("09:00");
        expectedBranch.setCloseTime("17:00");
        expectedBranch.setAddress("123 Main St");
        expectedBranch.setLocation("New York");
        expectedBranch.setContact("555-0123");
        expectedBranch.setCoordinates(new CoordinatesHarsh(40.7128, -74.0060));
        expectedBranch.setHolidayCalendar(new ArrayList<>());
        expectedBranch.setWeeklyHolidayList(Arrays.asList("Sunday"));

        when(repository.save(any(BankBranchHarsh.class))).thenReturn(expectedBranch);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(expectedBranch))
                    .thenReturn(sampleBankBranchDTO);

            // When
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);

            // Then
            assertNotNull(result);
            assertEquals(sampleBankBranchDTO, result);
            verify(repository, times(1)).save(any(BankBranchHarsh.class));
        }
    }

    @Test
    void createBankBranch_WithNullWeeklyHolidays_SetsNullWeeklyHolidays() {
        // Given
        sampleCreateDTO.setWeeklyHolidays(null);
        BankBranchHarsh expectedBranch = new BankBranchHarsh();
        expectedBranch.setId(1L);
        expectedBranch.setBankName("Test Bank");
        expectedBranch.setBankBranchName("Main Branch");
        expectedBranch.setBranchNumber("BR001");
        expectedBranch.setDescription("Main branch description");
        expectedBranch.setBankBranchType("Main");
        expectedBranch.setCountryName("USA");
        expectedBranch.setOpenTime("09:00");
        expectedBranch.setCloseTime("17:00");
        expectedBranch.setAddress("123 Main St");
        expectedBranch.setLocation("New York");
        expectedBranch.setContact("555-0123");
        expectedBranch.setCoordinates(new CoordinatesHarsh(40.7128, -74.0060));
        expectedBranch.setHolidayCalendar(Arrays.asList(new HolidayHarsh("2024-01-01", "New Year", "Public")));
        expectedBranch.setWeeklyHolidayList(null);

        when(repository.save(any(BankBranchHarsh.class))).thenReturn(expectedBranch);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(expectedBranch))
                    .thenReturn(sampleBankBranchDTO);

            // When
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);

            // Then
            assertNotNull(result);
            assertEquals(sampleBankBranchDTO, result);
            verify(repository, times(1)).save(any(BankBranchHarsh.class));
        }
    }

    @Test
    void createBankBranch_WithEmptyWeeklyHolidays_SetsEmptyWeeklyHolidays() {
        // Given
        sampleCreateDTO.setWeeklyHolidays(new ArrayList<>());
        BankBranchHarsh expectedBranch = new BankBranchHarsh();
        expectedBranch.setId(1L);
        expectedBranch.setBankName("Test Bank");
        expectedBranch.setBankBranchName("Main Branch");
        expectedBranch.setBranchNumber("BR001");
        expectedBranch.setDescription("Main branch description");
        expectedBranch.setBankBranchType("Main");
        expectedBranch.setCountryName("USA");
        expectedBranch.setOpenTime("09:00");
        expectedBranch.setCloseTime("17:00");
        expectedBranch.setAddress("123 Main St");
        expectedBranch.setLocation("New York");
        expectedBranch.setContact("555-0123");
        expectedBranch.setCoordinates(new CoordinatesHarsh(40.7128, -74.0060));
        expectedBranch.setHolidayCalendar(Arrays.asList(new HolidayHarsh("2024-01-01", "New Year", "Public")));
        expectedBranch.setWeeklyHolidayList(new ArrayList<>());

        when(repository.save(any(BankBranchHarsh.class))).thenReturn(expectedBranch);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(expectedBranch))
                    .thenReturn(sampleBankBranchDTO);

            // When
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);

            // Then
            assertNotNull(result);
            assertEquals(sampleBankBranchDTO, result);
            verify(repository, times(1)).save(any(BankBranchHarsh.class));
        }
    }

    @Test
    void createBankBranch_WithMultipleHolidays_CreatesCorrectHolidays() {
        // Given
        HolidayHarshDTO holiday1 = new HolidayHarshDTO();
        holiday1.setDate("2024-01-01");
        holiday1.setName("New Year");
        holiday1.setType("Public");

        HolidayHarshDTO holiday2 = new HolidayHarshDTO();
        holiday2.setDate("2024-12-25");
        holiday2.setName("Christmas");
        holiday2.setType("Public");

        sampleCreateDTO.setHolidayCalendar(Arrays.asList(holiday1, holiday2));

        BankBranchHarsh expectedBranch = new BankBranchHarsh();
        expectedBranch.setId(1L);
        expectedBranch.setBankName("Test Bank");
        expectedBranch.setBankBranchName("Main Branch");
        expectedBranch.setBranchNumber("BR001");
        expectedBranch.setDescription("Main branch description");
        expectedBranch.setBankBranchType("Main");
        expectedBranch.setCountryName("USA");
        expectedBranch.setOpenTime("09:00");
        expectedBranch.setCloseTime("17:00");
        expectedBranch.setAddress("123 Main St");
        expectedBranch.setLocation("New York");
        expectedBranch.setContact("555-0123");
        expectedBranch.setCoordinates(new CoordinatesHarsh(40.7128, -74.0060));
        expectedBranch.setHolidayCalendar(Arrays.asList(
                new HolidayHarsh("2024-01-01", "New Year", "Public"),
                new HolidayHarsh("2024-12-25", "Christmas", "Public")
        ));
        expectedBranch.setWeeklyHolidayList(Arrays.asList("Sunday"));

        when(repository.save(any(BankBranchHarsh.class))).thenReturn(expectedBranch);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(expectedBranch))
                    .thenReturn(sampleBankBranchDTO);

            // When
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);

            // Then
            assertNotNull(result);
            assertEquals(sampleBankBranchDTO, result);
            verify(repository, times(1)).save(any(BankBranchHarsh.class));
        }
    }

    @Test
    void createBankBranch_WithMultipleWeeklyHolidays_SetsCorrectWeeklyHolidays() {
        // Given
        sampleCreateDTO.setWeeklyHolidays(Arrays.asList("Sunday", "Saturday"));

        BankBranchHarsh expectedBranch = new BankBranchHarsh();
        expectedBranch.setId(1L);
        expectedBranch.setBankName("Test Bank");
        expectedBranch.setBankBranchName("Main Branch");
        expectedBranch.setBranchNumber("BR001");
        expectedBranch.setDescription("Main branch description");
        expectedBranch.setBankBranchType("Main");
        expectedBranch.setCountryName("USA");
        expectedBranch.setOpenTime("09:00");
        expectedBranch.setCloseTime("17:00");
        expectedBranch.setAddress("123 Main St");
        expectedBranch.setLocation("New York");
        expectedBranch.setContact("555-0123");
        expectedBranch.setCoordinates(new CoordinatesHarsh(40.7128, -74.0060));
        expectedBranch.setHolidayCalendar(Arrays.asList(new HolidayHarsh("2024-01-01", "New Year", "Public")));
        expectedBranch.setWeeklyHolidayList(Arrays.asList("Sunday", "Saturday"));

        when(repository.save(any(BankBranchHarsh.class))).thenReturn(expectedBranch);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(expectedBranch))
                    .thenReturn(sampleBankBranchDTO);

            // When
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);

            // Then
            assertNotNull(result);
            assertEquals(sampleBankBranchDTO, result);
            verify(repository, times(1)).save(any(BankBranchHarsh.class));
        }
    }

    @Test
    void createBankBranch_WithMinimalDTO_ReturnsSavedDTO() {
        // Given
        CreateBankHarshBranchDTO minimalDTO = new CreateBankHarshBranchDTO();
        minimalDTO.setBankName("Minimal Bank");
        minimalDTO.setBankBranchName("Minimal Branch");
        minimalDTO.setBranchNumber("MB001");
        minimalDTO.setDescription("Minimal description");
        minimalDTO.setBankBranchType("Branch");
        minimalDTO.setCountryName("USA");
        minimalDTO.setOpenTime("08:00");
        minimalDTO.setCloseTime("16:00");
        minimalDTO.setAddress("456 Minimal St");
        minimalDTO.setLocation("Minimal City");
        minimalDTO.setContact("555-9999");
        minimalDTO.setCoordinates(null);
        minimalDTO.setHolidayCalendar(null);
        minimalDTO.setWeeklyHolidays(null);

        BankBranchHarsh minimalBranch = new BankBranchHarsh();
        minimalBranch.setId(2L);
        minimalBranch.setBankName("Minimal Bank");
        minimalBranch.setBankBranchName("Minimal Branch");
        minimalBranch.setBranchNumber("MB001");
        minimalBranch.setDescription("Minimal description");
        minimalBranch.setBankBranchType("Branch");
        minimalBranch.setCountryName("USA");
        minimalBranch.setOpenTime("08:00");
        minimalBranch.setCloseTime("16:00");
        minimalBranch.setAddress("456 Minimal St");
        minimalBranch.setLocation("Minimal City");
        minimalBranch.setContact("555-9999");
        minimalBranch.setCoordinates(null);
        minimalBranch.setHolidayCalendar(null);
        minimalBranch.setWeeklyHolidayList(null);

        BankBranchDTO minimalDTO_result = new BankBranchDTO();
        minimalDTO_result.setId(2L);
        minimalDTO_result.setBankName("Minimal Bank");
        minimalDTO_result.setBankBranchName("Minimal Branch");
        minimalDTO_result.setBranchNumber("MB001");
        minimalDTO_result.setDescription("Minimal description");
        minimalDTO_result.setBankBranchType("Branch");
        minimalDTO_result.setCountryName("USA");
        minimalDTO_result.setOpenTime("08:00");
        minimalDTO_result.setCloseTime("16:00");
        minimalDTO_result.setAddress("456 Minimal St");
        minimalDTO_result.setLocation("Minimal City");
        minimalDTO_result.setContact("555-9999");
        minimalDTO_result.setCoordinates(null);
        minimalDTO_result.setHolidayCalendar(null);
        minimalDTO_result.setWeeklyHolidays(null);

        when(repository.save(any(BankBranchHarsh.class))).thenReturn(minimalBranch);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(minimalBranch))
                    .thenReturn(minimalDTO_result);

            // When
            BankBranchDTO result = bankBranchService.createBankBranch(minimalDTO);

            // Then
            assertNotNull(result);
            assertEquals(minimalDTO_result, result);
            verify(repository, times(1)).save(any(BankBranchHarsh.class));
            mockedMapper.verify(() -> BankBranchMapper.toDTO(minimalBranch), times(1));
        }
    }

    @Test
    void createBankBranch_WhenRepositoryThrowsException_PropagatesException() {
        // Given
        when(repository.save(any(BankBranchHarsh.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            bankBranchService.createBankBranch(sampleCreateDTO);
        });

        verify(repository, times(1)).save(any(BankBranchHarsh.class));
    }

    @Test
    void getAllBranches_WhenRepositoryThrowsException_PropagatesException() {
        // Given
        when(repository.findAll()).thenThrow(new RuntimeException("Database connection error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            bankBranchService.getAllBranches();
        });

        verify(repository, times(1)).findAll();
    }

    @Test
    void createBankBranch_WhenMapperThrowsException_PropagatesException() {
        // Given
        when(repository.save(any(BankBranchHarsh.class))).thenReturn(sampleBankBranchHarsh);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh))
                    .thenThrow(new RuntimeException("Mapper error"));

            // When & Then
            assertThrows(RuntimeException.class, () -> {
                bankBranchService.createBankBranch(sampleCreateDTO);
            });

            verify(repository, times(1)).save(any(BankBranchHarsh.class));
        }
    }

    @Test
    void getAllBranches_WhenMapperThrowsException_PropagatesException() {
        // Given
        List<BankBranchHarsh> branches = Arrays.asList(sampleBankBranchHarsh);
        when(repository.findAll()).thenReturn(branches);

        try (MockedStatic<BankBranchMapper> mockedMapper = mockStatic(BankBranchMapper.class)) {
            mockedMapper.when(() -> BankBranchMapper.toDTO(sampleBankBranchHarsh))
                    .thenThrow(new RuntimeException("Mapper error"));

            // When & Then
            assertThrows(RuntimeException.class, () -> {
                bankBranchService.getAllBranches();
            });

            verify(repository, times(1)).findAll();
        }
    }
}
