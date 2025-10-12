package com.example.card.adapter.api.services.impl;

import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.domain.dto.BankDetailsNewRequestDto;
import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.repository.BankDetailsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankDetailsImplTest {

    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private BankDetailsImpl bankDetailsService;

    @Test
    void getBankDetails_WhenEntityFound_ReturnsResponseDto() {
        // Arrange
        BankDetailsEntity entity = new BankDetailsEntity();
        entity.setId(1L);
        entity.setNameEn("Dukhan Bank");
        entity.setNameAr("بنك دخان");
        entity.setMail("info@dukhanbank.com");
        entity.setContact(12345678L);
        entity.setInternationalContact("+9712345678");
        entity.setDisplayOrder(0);

        when(bankDetailsRepository.findById(1L)).thenReturn(Optional.of(entity));

        BankDetailsResponseDto bankDetails = bankDetailsService.getBankDetails();

        assertNotNull(bankDetails);
        assertEquals("info@dukhanbank.com", bankDetails.getMail());
        assertEquals(12345678L, bankDetails.getContact());

        verify(bankDetailsRepository, times(1)).findById(1L);
    }

    @Test
    void getBankDetails_WhenNoEntityFound_ThrowsException() {

        when(bankDetailsRepository.findById(anyLong()))
                .thenThrow(new ResourceNotFoundException("No bank details found"));

        ResourceNotFoundException foundException = assertThrows(ResourceNotFoundException.class, () -> {
            bankDetailsService.getBankDetails(); // Make sure this calls findById with a long
        });

        assertNotNull(foundException);
        assertEquals("No bank details found", foundException.getMessage());
        verify(bankDetailsRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveBankDetailsNew_ValidRequest_SavesAndReturnsEntity() {
        // Arrange
        BankDetailsNewRequestDto dto = BankDetailsNewRequestDto.builder()
                .nameEn("Test Bank")
                .mail("test@bank.com")
                .contact(987654321L)
                .internationalContact("+9712345678")
                .displayOrder(1)
                .build();

        BankDetailsEntity savedEntity = new BankDetailsEntity();
        savedEntity.setId(10L);

        when(bankDetailsRepository.save(any(BankDetailsEntity.class))).thenReturn(savedEntity);

        BankDetailsEntity result = bankDetailsService.saveBankDetailsNew(dto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(bankDetailsRepository, times(1)).save(any(BankDetailsEntity.class));
    }
}
