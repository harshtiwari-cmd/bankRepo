package com.example.card.service.impl;



import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.entity.AtmEntity;
import com.example.card.constrants.mapper.AtmMapper;
import com.example.card.constrants.model.Coordinates;
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

//    @BeforeEach
//    void setUp() {
//        Coordinates coordinates = new Coordinates(12.9716, 77.5946);
//
//        requestDto = AtmRequestDto.builder()
//                .atmId("AB123456")
//                .branchId("402519")
//                .siteName("central mall")
//                .streetName("panjagutta road")
//                .townName("Hyderabad")
//                .country("IN")
//                .postCode("560038")
//                .coordinates(new Coordinates(12.97878, 77.9999))
//                .supportedLanguages(List.of("en", "hi","or"))
//                .atmServices(List.of("cashWithdrawal", "MiniStateMent"))
//                .supportedCurrencies(List.of("INR"))
//                .minimumPossibleAmount(100)
//                .openTime("08:00")
//                .build();
//
//        savedEntity = AtmEntity.builder()
//                .id(1L)
//                .atmId("ATM123")
//                .branchId("BR001")
//                .siteName("Mall")
//                .streetName("Main Street")
//                .townName("Bangalore")
//                .country("IN")
//                .postCode("560001")
//                .coordinates(coordinates)
//                .supportedLanguages("en,hi")
//                .atmServices(List.of("cash-withdrawal", "balance-enquiry"))
//                .supportedCurrencies("INR")
//                .minimumPossibleAmount(100)
//                .openTime("08:00")
//                .build();
//    }
//
//    @Test
//    void testRegisterAtm_success() {
//        when(atmRepo.save(any())).thenReturn(savedEntity);
//
//        AtmResponseDto response = atmService.registerAtm(requestDto);
//
//        assertNotNull(response);
//        assertEquals("ATM123", response.getAtmId());
//        assertEquals("BR001", response.getBranchId());
//        assertEquals("Bangalore", response.getTownName());
//        assertEquals("IN", response.getCountry());
//        assertEquals("08:00", response.getOpenTime());
//    }
//
//    @Test
//    void testGetAtm_returnsData() {
//        AtmResponseDto dto = AtmResponseDto.builder()
//                .atmId("ATM123")
//                .branchId("BR001")
//                .siteName("Mall")
//                .townName("Bangalore")
//                .country("IN")
//                .openTime("08:00")
//                .build();
//
//        when(atmRepo.findAll()).thenReturn(List.of(savedEntity));
//        when(atmMapper.toDto(savedEntity)).thenReturn(dto);
//
//        List<AtmResponseDto> result = atmService.getAtm();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("ATM123", result.get(0).getAtmId());
//    }
//
//    @Test
//    void testGetAtm_returnsEmptyList() {
//        when(atmRepo.findAll()).thenReturn(Collections.emptyList());
//
//        List<AtmResponseDto> result = atmService.getAtm();
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
}
