package com.example.card.adapter.api.services.impl;

import com.example.card.constrants.mapper.KioskMapper;
import com.example.card.domain.dto.*;
import com.example.card.domain.model.Kiosk;
import com.example.card.exceptions.BusinessException;
import com.example.card.repository.KioskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KioskServiceImplTest {

    @Mock
    private KioskRepository kioskRepository;

    @Mock
    private KioskMapper kioskMapper;

    @InjectMocks
    private KioskServiceImpl kioskService;

    private KioskRequestDTO validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new KioskRequestDTO();
        validRequest.setKioskId("K001");
        validRequest.setBranchId("BR001");
        validRequest.setName("Lobby Kiosk");
        validRequest.setDescription("Main branch kiosk");

        LocationDTO location = new LocationDTO();
        location.setTownName("New York");
        location.setCountry("USA");
        validRequest.setLocation(location);

        CoordinatesDTO coordinates = new CoordinatesDTO();
        coordinates.setLatitude(40.7128);
        coordinates.setLongitude(-74.0060);
        validRequest.setCoordinates(coordinates);

        validRequest.setKioskServices(List.of("PRINT", "SCAN"));
        validRequest.setOpenTime("08:00");
        validRequest.setCloseTime("17:00");

        HolidayCalendarDTO holiday = new HolidayCalendarDTO();
        holiday.setName("Christmas");
        holiday.setDate("2025-12-25");
        validRequest.setHolidayCalendar(List.of(holiday));

        validRequest.setWeeklyHolidays(List.of("SUNDAY"));
    }

    @Test
    void createKiosk_success() {
        Kiosk entity = new Kiosk();
        entity.setKioskId("K001");

        KioskResponseDTO response = new KioskResponseDTO();
        response.setKioskId("K001");

        when(kioskRepository.existsByKioskId("K001")).thenReturn(false);
        when(kioskRepository.existsByName("Lobby Kiosk")).thenReturn(false);
        when(kioskMapper.toEntity(validRequest)).thenReturn(entity);
        when(kioskRepository.save(entity)).thenReturn(entity);
        when(kioskMapper.toDto(entity)).thenReturn(response);

        KioskResponseDTO result = kioskService.createKiosk(validRequest);

        assertThat(result).isNotNull();
        assertThat(result.getKioskId()).isEqualTo("K001");

        verify(kioskRepository).save(entity);
        verify(kioskMapper).toEntity(validRequest);
        verify(kioskMapper).toDto(entity);
    }

    @Test
    void createKiosk_shouldThrow_whenKioskIdIsNull() {
        validRequest.setKioskId(null);

        BusinessException ex = catchThrowableOfType(() ->
                kioskService.createKiosk(validRequest), BusinessException.class);

        assertThat(ex.getErrorCode()).isEqualTo("KIOSK_ID_NULL");
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createKiosk_shouldThrow_whenKioskIdIsBlank() {
        validRequest.setKioskId("   ");

        BusinessException ex = catchThrowableOfType(() ->
                kioskService.createKiosk(validRequest), BusinessException.class);

        assertThat(ex.getErrorCode()).isEqualTo("KIOSK_ID_EMPTY");
    }

    @Test
    void createKiosk_shouldThrow_whenKioskIdAlreadyExists() {
        when(kioskRepository.existsByKioskId("K001")).thenReturn(true);

        BusinessException ex = catchThrowableOfType(() ->
                kioskService.createKiosk(validRequest), BusinessException.class);

        assertThat(ex.getErrorCode()).isEqualTo("DUPLICATE_KIOSK_ID");
    }

    @Test
    void createKiosk_shouldThrow_whenBranchIdIsNull() {
        validRequest.setBranchId(null);

        BusinessException ex = catchThrowableOfType(() ->
                kioskService.createKiosk(validRequest), BusinessException.class);

        assertThat(ex.getErrorCode()).isEqualTo("BRANCH_ID_NULL");
    }

    @Test
    void createKiosk_shouldThrow_whenBranchIdIsBlank() {
        validRequest.setBranchId("  ");

        BusinessException ex = catchThrowableOfType(() ->
                kioskService.createKiosk(validRequest), BusinessException.class);

        assertThat(ex.getErrorCode()).isEqualTo("BRANCH_ID_EMPTY");
        assertThat(ex.getMessage()).isEqualTo("Branch ID cannot be empty");
    }

    @Test
    void createKiosk_shouldThrow_whenNameIsNull() {
        validRequest.setName(null);

        BusinessException ex = catchThrowableOfType(() ->
                kioskService.createKiosk(validRequest), BusinessException.class);

        assertThat(ex.getErrorCode()).isEqualTo("NAME_NULL");
        assertThat(ex.getMessage()).isEqualTo("Kiosk name cannot be null");
    }

    @Test
    void createKiosk_shouldThrow_whenNameIsBlank() {
        validRequest.setName(" ");

        BusinessException ex = catchThrowableOfType(() ->
                kioskService.createKiosk(validRequest), BusinessException.class);

        assertThat(ex.getErrorCode()).isEqualTo("NAME_EMPTY");
        assertThat(ex.getMessage()).isEqualTo("Kiosk name cannot be empty");
    }

    @Test
    void createKiosk_shouldThrow_whenDuplicateNameExists() {
        when(kioskRepository.existsByKioskId("K001")).thenReturn(false);
        when(kioskRepository.existsByName("Lobby Kiosk")).thenReturn(true);

        BusinessException ex = catchThrowableOfType(() ->
                kioskService.createKiosk(validRequest), BusinessException.class);

        assertThat(ex.getErrorCode()).isEqualTo("DUPLICATE_NAME");
    }

    @Test
    void createKiosk_shouldThrow_whenLocationIsNull() {
        validRequest.setLocation(null);

        BusinessException ex = catchThrowableOfType(() ->
                kioskService.createKiosk(validRequest), BusinessException.class);

        assertThat(ex.getErrorCode()).isEqualTo("LOCATION_NULL");
    }

    @Test
    void getKiosk_shouldReturnMappedList() {
        Kiosk kiosk1 = new Kiosk();
        kiosk1.setKioskId("K001");

        Kiosk kiosk2 = new Kiosk();
        kiosk2.setKioskId("K002");

        KioskResponseDTO dto1 = new KioskResponseDTO();
        dto1.setKioskId("K001");

        KioskResponseDTO dto2 = new KioskResponseDTO();
        dto2.setKioskId("K002");

        when(kioskRepository.findAll()).thenReturn(List.of(kiosk1, kiosk2));
        when(kioskMapper.toDto(kiosk1)).thenReturn(dto1);
        when(kioskMapper.toDto(kiosk2)).thenReturn(dto2);

        List<KioskResponseDTO> result = kioskService.getKiosk();

        assertThat(result).hasSize(2);
        assertThat(result).extracting("kioskId").containsExactly("K001", "K002");
    }

    @Test
    void getKiosk_shouldThrowException_whenRepositoryFails() {
        when(kioskRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        Throwable ex = catchThrowable(() -> kioskService.getKiosk());

        assertThat(ex)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("DB error");
    }
}
