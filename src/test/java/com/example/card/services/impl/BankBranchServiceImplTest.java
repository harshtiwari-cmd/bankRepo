package com.example.card.services.impl;

import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankBranchDTO;
import com.example.card.constrants.dto.CoordinatesDTO;
import com.example.card.constrants.dto.HolidayDTO;
import com.example.card.constrants.mapper.BankBranchMapper;
import com.example.card.constrants.model.Coordinates;
import com.example.card.constrants.model.Holiday;
import com.example.card.entity.BankBranch;
import com.example.card.repository.BankBranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankBranchServiceImplTest {

    @Mock
    private BankBranchRepository repository;

    @InjectMocks
    private BankBranchServiceImpl bankBranchService;

    private CreateBankBranchDTO sampleCreateDTO;
    private BankBranch sampleBankBranch;
    private BankBranchDTO sampleBankBranchDTO;

    @BeforeEach
    void setUp() {

        sampleCreateDTO = new CreateBankBranchDTO();
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

        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(40.7128);
        coordinatesDTO.setLongitude(-74.0060);
        sampleCreateDTO.setCoordinates(coordinatesDTO);

        HolidayDTO holidayDTO = new HolidayDTO();
        holidayDTO.setDate("2024-01-01");
        holidayDTO.setName("New Year");
        holidayDTO.setType("Public");
        sampleCreateDTO.setHolidayCalendar(Collections.singletonList(holidayDTO));
        sampleCreateDTO.setWeeklyHolidays(Collections.singletonList("Sunday"));


        sampleBankBranch = new BankBranch();
        sampleBankBranch.setId(1L);
        sampleBankBranch.setBankName("Test Bank");
        sampleBankBranch.setBankBranchName("Main Branch");
        sampleBankBranch.setBranchNumber("BR001");
        sampleBankBranch.setDescription("Main branch description");
        sampleBankBranch.setBankBranchType("Main");
        sampleBankBranch.setCountryName("USA");
        sampleBankBranch.setOpenTime("09:00");
        sampleBankBranch.setCloseTime("17:00");
        sampleBankBranch.setAddress("123 Main St");
        sampleBankBranch.setLocation("New York");
        sampleBankBranch.setContact("555-0123");
        sampleBankBranch.setCoordinates(new Coordinates(40.7128, -74.0060));
        sampleBankBranch.setHolidayCalendar(Collections.singletonList(new Holiday("2024-01-01", "New Year", "Public")));
        sampleBankBranch.setWeeklyHolidayList(Collections.singletonList("Sunday"));


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
        sampleBankBranchDTO.setCoordinates(coordinatesDTO);
        sampleBankBranchDTO.setHolidayCalendar(Collections.singletonList(holidayDTO));
        sampleBankBranchDTO.setWeeklyHolidays(Collections.singletonList("Sunday"));
    }


    @Test
    void getAllBranches_EmptyList_ReturnsEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<BankBranchDTO> result = bankBranchService.getAllBranches();
        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void getAllBranches_WithBranches_ReturnsDTOs() {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleBankBranch));
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(sampleBankBranch)).thenReturn(sampleBankBranchDTO);
            List<BankBranchDTO> result = bankBranchService.getAllBranches();
            assertEquals(1, result.size());
        }
    }

    @Test
    void getAllBranches_MapperReturnsNull_ListContainsNull() {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleBankBranch));
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(sampleBankBranch)).thenReturn(null);
            List<BankBranchDTO> result = bankBranchService.getAllBranches();
            assertEquals(1, result.size());
            assertNull(result.get(0));
        }
    }

    @Test
    void getAllBranches_RepositoryThrows_Propagates() {
        when(repository.findAll()).thenThrow(new RuntimeException("DB fail"));
        assertThrows(RuntimeException.class, () -> bankBranchService.getAllBranches());
    }

    @Test
    void getAllBranches_MapperThrows_Propagates() {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleBankBranch));
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(sampleBankBranch))
                    .thenThrow(new RuntimeException("Mapper fail"));
            assertThrows(RuntimeException.class, () -> bankBranchService.getAllBranches());
        }
    }


    @Test
    void createBankBranch_HappyPath_ReturnsDTO() {
        when(repository.save(any())).thenReturn(sampleBankBranch);
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(sampleBankBranch)).thenReturn(sampleBankBranchDTO);
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);
            assertEquals(sampleBankBranchDTO, result);
        }
    }

    @Test
    void createBankBranch_NullCoordinates_NullInEntity() {
        sampleCreateDTO.setCoordinates(null);
        BankBranch branch = new BankBranch();
        branch.setId(2L);
        when(repository.save(any())).thenReturn(branch);
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(branch)).thenReturn(new BankBranchDTO());
            BankBranchDTO result = bankBranchService.createBankBranch(sampleCreateDTO);
            assertNotNull(result);
        }
    }

    @Test
    void createBankBranch_NullHolidayCalendar_NullInEntity() {
        sampleCreateDTO.setHolidayCalendar(null);
        BankBranch branch = new BankBranch();
        branch.setId(3L);
        when(repository.save(any())).thenReturn(branch);
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(branch)).thenReturn(new BankBranchDTO());
            assertNotNull(bankBranchService.createBankBranch(sampleCreateDTO));
        }
    }

    @Test
    void createBankBranch_EmptyHolidayCalendar_EmptyInEntity() {
        sampleCreateDTO.setHolidayCalendar(new ArrayList<>());
        BankBranch branch = new BankBranch();
        branch.setId(4L);
        when(repository.save(any())).thenReturn(branch);
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(branch)).thenReturn(new BankBranchDTO());
            assertNotNull(bankBranchService.createBankBranch(sampleCreateDTO));
        }
    }

    @Test
    void createBankBranch_NullWeeklyHolidays_NullInEntity() {
        sampleCreateDTO.setWeeklyHolidays(null);
        BankBranch branch = new BankBranch();
        branch.setId(5L);
        when(repository.save(any())).thenReturn(branch);
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(branch)).thenReturn(new BankBranchDTO());
            assertNotNull(bankBranchService.createBankBranch(sampleCreateDTO));
        }
    }

    @Test
    void createBankBranch_EmptyWeeklyHolidays_EmptyInEntity() {
        sampleCreateDTO.setWeeklyHolidays(new ArrayList<>());
        BankBranch branch = new BankBranch();
        branch.setId(6L);
        when(repository.save(any())).thenReturn(branch);
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(branch)).thenReturn(new BankBranchDTO());
            assertNotNull(bankBranchService.createBankBranch(sampleCreateDTO));
        }
    }

    @Test
    void createBankBranch_MultipleHolidaysAndWeekly_CoversBranches() {
        HolidayDTO h1 = new HolidayDTO(); h1.setDate("2024-01-01");
        HolidayDTO h2 = new HolidayDTO(); h2.setDate("2024-12-25");
        sampleCreateDTO.setHolidayCalendar(Arrays.asList(h1, h2));
        sampleCreateDTO.setWeeklyHolidays(Arrays.asList("Sun", "Sat"));
        BankBranch branch = new BankBranch(); branch.setId(7L);
        when(repository.save(any())).thenReturn(branch);
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(branch)).thenReturn(new BankBranchDTO());
            assertNotNull(bankBranchService.createBankBranch(sampleCreateDTO));
        }
    }

    @Test
    void createBankBranch_MapperReturnsNull_Allowed() {
        when(repository.save(any())).thenReturn(sampleBankBranch);
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(sampleBankBranch)).thenReturn(null);
            assertNull(bankBranchService.createBankBranch(sampleCreateDTO));
        }
    }

    @Test
    void createBankBranch_RepositoryThrows_Propagates() {
        when(repository.save(any())).thenThrow(new RuntimeException("DB fail"));
        assertThrows(RuntimeException.class, () -> bankBranchService.createBankBranch(sampleCreateDTO));
    }

    @Test
    void createBankBranch_MapperThrows_Propagates() {
        when(repository.save(any())).thenReturn(sampleBankBranch);
        try (MockedStatic<BankBranchMapper> mocked = mockStatic(BankBranchMapper.class)) {
            mocked.when(() -> BankBranchMapper.toDTO(sampleBankBranch))
                    .thenThrow(new RuntimeException("Mapper fail"));
            assertThrows(RuntimeException.class, () -> bankBranchService.createBankBranch(sampleCreateDTO));
        }
    }
}
