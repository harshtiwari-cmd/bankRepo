package com.example.card.service.impl;

import com.example.card.domain.dto.AtmRequestDto;
import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.constrants.entity.AtmEntity;
import com.example.card.constrants.mapper.AtmMapper;
import com.example.card.repository.Atm_Repo;
import com.example.card.adapter.api.services.impl.AtmServiceImpl;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtmServiceImplTest {

    @Mock
    private Atm_Repo atmRepo;

    @Mock
    private AtmMapper atmMapper;

    @InjectMocks
    private AtmServiceImpl atmService;

    private AtmRequestDto requestDto;
    private AtmEntity savedEntity;

    @BeforeEach
    void setUp() {
        requestDto = AtmRequestDto.builder()
                .arabicName("aaaa")
                .cashDeposit(true)
                .cashOut(true)
                .chequeDeposit(false)
                .city("Hyderabad")
                .cityInArabic("bbbb")
                .code("ATM001")
                .contactDetails("1800-ATM")
                .country("IN")
                .disablePeople(true)
                .fullAddress("Panjagutta Road, Central Mall")
                .fullAddressArb("yyyyyy")
                .latitude("17.385044")
                .longitude("78.486671")
                .onlineLocation(true)
                .timing("08:00 - 20:00")
                .typeLocation("Indoor")
                .workingHours("08:00 to 20:00")
                .workingHoursInArb("20:00 08:00")
                .build();

        savedEntity = AtmEntity.builder()
                .id(1L)
                .arabicName("aaaa")
                .cashDeposit(true)
                .cashOut(true)
                .chequeDeposit(false)
                .city("Hyderabad")
                .cityInArabic("bbbb")
                .code("ATM001")
                .contactDetails("1800-ATM")
                .country("IN")
                .disablePeople(true)
                .fullAddress("Panjagutta Road, Central Mall")
                .fullAddressArb("yyyyyy")
                .latitude("17.385044")
                .longitude("78.486671")
                .onlineLocation(true)
                .timing("08:00 - 20:00")
                .typeLocation("Indoor")
                .workingHours("08:00 to 20:00")
                .workingHoursInArb("20:00 08:00")
                .build();
    }

    @Test
    void testRegisterAtm_success() {
        when(atmRepo.save(any())).thenReturn(savedEntity);

        AtmResponseDto response = atmService.registerAtm(requestDto);

        assertNotNull(response);
        assertEquals("ATM001", response.getCode());
        assertEquals("Hyderabad", response.getCity());
        assertEquals("IN", response.getCountry());
        assertEquals("08:00 - 20:00", response.getTiming());
    }

    @Test
    void testGetAtm_returnsData() {
        AtmResponseDto dto = AtmResponseDto.builder()
                .code("ATM001")
                .city("Hyderabad")
                .country("IN")
                .timing("08:00 - 20:00")
                .build();

        when(atmRepo.findAll()).thenReturn(List.of(savedEntity));

        List<AtmResponseDto> result = atmService.getAtm();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ATM001", result.get(0).getCode());
    }

    @Test
    void testGetAtm_returnsEmptyList() {
        when(atmRepo.findAll()).thenReturn(Collections.emptyList());

        List<AtmResponseDto> result = atmService.getAtm();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testValidationFails_onMissingRequiredFields() {
        AtmRequestDto invalidDto = AtmRequestDto.builder()
                .code("")
                .cashDeposit(null)
                .cashOut(null)
                .chequeDeposit(null)
                .disablePeople(null)
                .onlineLocation(null)
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<AtmRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(6, violations.size());
    }

    @Test
    void testRegisterAtm_partialRequest_stillWorks() {
        AtmRequestDto partialDto = AtmRequestDto.builder()
                .cashDeposit(true)
                .cashOut(true)
                .chequeDeposit(true)
                .disablePeople(false)
                .code("ATM002")
                .onlineLocation(false)
                .build();

        AtmEntity partialEntity = AtmEntity.builder()
                .id(2L)
                .code("ATM002")
                .country("IN")
                .city("Mumbai")
                .build();

        when(atmRepo.save(any())).thenReturn(partialEntity);

        AtmResponseDto response = atmService.registerAtm(partialDto);

        assertNotNull(response);
        assertEquals("ATM002", response.getCode());
        assertEquals("IN", response.getCountry());
        assertEquals("Mumbai", response.getCity());
    }

    @Test
    void testRegisterAtm_throwsException() {
        when(atmRepo.save(any())).thenThrow(new RuntimeException("DB Save Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            atmService.registerAtm(requestDto);
        });

        assertEquals("DB Save Error", exception.getMessage());
    }
}
