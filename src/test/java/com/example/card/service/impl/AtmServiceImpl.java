package com.example.card.service.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.entity.AtmEntity;
import com.example.card.repository.Atm_Repo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AtmServiceImpl {

    @Mock
    private Atm_Repo atmRepository;

    @InjectMocks
    private com.example.card.services.impl.AtmServiceImpl atmService;

    public AtmServiceImpl()
    {
        MockitoAnnotations.openMocks(this);

    }
    @Test
    void testRegisterAtm()
    {
        AtmRequestDto dto= AtmRequestDto.builder()
                .atmId("AB123456")
                .branchId("402519")
                .siteName("central mall")
                .supportedLanguages(List.of("en", "hi"))
                .supportedCurrencies(List.of("INR"))
                .townName("Hyderabad")
                .country("IN")
                .openTime("08:00")
                .build();

        AtmEntity saved=AtmEntity.builder()
                .id(3L)
                .atmId("AB123456")
                .branchId("402519")
                .siteName("central mall")
                .townName("Hyderabad")
                .country("IN")
                .openTime("08:00")
                .build();
        when(atmRepository.save(any())).thenReturn(saved);
        assertEquals("AB123456",atmService.registerAtm(dto).getAtmId());
    }
}
