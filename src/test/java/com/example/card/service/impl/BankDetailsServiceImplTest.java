package com.example.card.service.impl;

import com.example.card.domain.dto.BankDetailsDto;
import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.constrants.mapper.BankDetailsMapper;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.repository.BankDetailsRepository;
import com.example.card.adapter.api.services.impl.BankDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankDetailsServiceImplTest {

    @Mock
    private BankDetailsRepository repository;

    @Mock
    private BankDetailsMapper mapper;

    @InjectMocks
    private BankDetailsImpl service;

    private BankDetailsDto validDto;
    private BankDetailsEntity entity;

    @BeforeEach
    void setup() {
        validDto = new BankDetailsDto();
        validDto.setName("Dukhan");
        validDto.setMail("dukhan@gmail.com");
        validDto.setContact(123456789L);
        validDto.setInternationalContact("987654");

        entity = new BankDetailsEntity();
        entity.setName("Dukhan");
        entity.setMail("dukhan@gmail.com");
        entity.setContact(123456789L);
        entity.setInternationalContact("987654");
    }

    @Test
    void testCreateBankDetails_success() {
        when(repository.save(any(BankDetailsEntity.class))).thenReturn(entity);

        BankDetailsEntity result = service.createBankDetails(validDto);

        assertNotNull(result);
        assertEquals("dukhan@gmail.com", result.getMail());
        assertEquals("Dukhan", result.getName());
    }

    @Test
    void testCreateBankDetails_nullDto_throwsException() {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.createBankDetails(null);
        });

        assertEquals("Account details are required ", exception.getMessage());
    }

    @Test
    void testCreateBankDetails_nullMail_throwsException() {
        validDto.setMail(null);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.createBankDetails(validDto);
        });

        assertEquals("Account details are required ", exception.getMessage());
    }

    @Test
    void testGetBankDetails_success() {
        when(repository.findTopBy()).thenReturn(entity);

        BankDetailsResponseDto result = service.getbankDetails();

        assertNotNull(result);
        assertEquals("Dukhan", result.getName());
        assertEquals("dukhan@gmail.com", result.getMail());
        assertEquals(123456789L, result.getContact());
        assertEquals("987654", result.getInternationalContact());
    }


}