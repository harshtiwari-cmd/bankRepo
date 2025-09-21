package com.example.card.service.impl;

import com.example.card.constrants.dto.BankDetailsDto;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.repository.BankDetailsRepository;
import com.example.card.services.impl.BankDetailsImpl;
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

    @InjectMocks
    private BankDetailsImpl service;

    @Test
    void testCreateBankDetails_success() {
        BankDetailsDto dto = new BankDetailsDto();
        dto.setMail("sbi@gmail.com");

        BankDetailsEntity entity = new BankDetailsEntity();
        entity.setMail("sbi@gmail.com");

        when(repository.save(any())).thenReturn(entity);

        BankDetailsEntity result = service.createBankDetails(dto);

        assertNotNull(result);
        assertEquals("sbi@gmail.com", result.getMail());
    }
}
