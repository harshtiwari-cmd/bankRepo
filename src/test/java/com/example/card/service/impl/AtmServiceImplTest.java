package com.example.card.service.impl;


import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.entity.AtmEntity;
import com.example.card.constrants.mapper.AtmMapper;
import com.example.card.repository.Atm_Repo;
import com.example.card.services.impl.AtmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

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
    void setup() {
        requestDto = AtmRequestDto.builder()
                .atmId("ATM001")
                .branchId("BR123")
                .siteName("City Center")
                .streetName("Main Street")
                .townName("Delhi")
                .country("IN")
                .postCode("110001")
                .latitude(28.6139)
                .longitude(77.2090)
                .atmServices(List.of("cash-withdrawal"))
                .supportedLanguages(List.of("en", "hi"))
                .supportedCurrencies(List.of("INR", "USD"))
                .minimumPossibleAmount(100)
                .openTime("09:00")
                .build();

        savedEntity = AtmEntity.builder()
                .id(1L)
                .atmId("ATM001")
                .branchId("BR123")
                .siteName("City Center")
                .townName("Delhi")
                .country("IN")
                .openTime("09:00")
                .build();
    }

    @Test
    void testRegisterAtm_success() {
        when(atmRepo.save(any())).thenReturn(savedEntity);

        var response = atmService.registerAtm(requestDto);

        assertNotNull(response);
        assertEquals("ATM001", response.getAtmId());
        assertEquals("BR123", response.getBranchId());
        assertEquals("Delhi", response.getTownName());
    }

    @Test
    void testGetAtm_withData() {
        AtmEntity entity = AtmEntity.builder()
                .atmId("ATM123")
                .branchId("BR456")
                .build();

        AtmResponseDto dto = AtmResponseDto.builder()
                .atmId("ATM123")
                .branchId("BR456")
                .build();

        when(atmRepo.findAll()).thenReturn(List.of(entity));
        when(atmMapper.toDto(entity)).thenReturn(dto);

        List<AtmResponseDto> result = atmService.getAtm();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ATM123", result.get(0).getAtmId());
    }

    @Test
    void testGetAtm_emptyList() {
        when(atmRepo.findAll()).thenReturn(Collections.emptyList());

        List<AtmResponseDto> result = atmService.getAtm();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
