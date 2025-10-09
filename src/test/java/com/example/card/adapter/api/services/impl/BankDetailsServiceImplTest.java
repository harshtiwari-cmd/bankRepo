package com.example.card.adapter.api.services.impl;

import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.constrants.mapper.BankDetailsMapper;
import com.example.card.repository.BankDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BankDetailsServiceImplTest {

    @Mock
    private BankDetailsRepository repository;

    @Mock
    private BankDetailsMapper mapper;

    @InjectMocks
    private BankDetailsImpl service;

    private BankDetailsEntity entity;

    @BeforeEach
    void setup() {
    }

    @Test
    void testCreateBankDetails_success() {

    }

    @Test
    void testCreateBankDetails_nullDto_throwsException() {;
    }

    @Test
    void testCreateBankDetails_nullMail_throwsException() {

    }

    @Test
    void testGetBankDetails_success() {
//        when(repository.findTopBy()).thenReturn(entity);
//
//        BankDetailsResponseDto result = service.getbankDetails();
//
//        assertNotNull(result);
//        assertEquals("Dukhan", result.getName());
//        assertEquals("dukhan@gmail.com", result.getMail());
//        assertEquals(123456789L, result.getContact());
//        assertEquals("987654", result.getInternationalContact());
    }


}