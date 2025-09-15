package com.example.card.controller.impl;

import com.example.card.dto.BankDetailsDto;
import com.example.card.entity.BankDetailsEntity;
import com.example.card.services.BankDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;


public class BankDetailsControllerImplTest {


    @Mock
    private BankDetailsService bankDetailsService;

    @InjectMocks
    private BankDetailsControllerImpl contrlr;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBankDetails_success() {
        // Given
        BankDetailsDto dto = new BankDetailsDto();
        dto.setMail("test@example.com");
        dto.setContact(1234567890L);
        dto.setAddress("Test Bank");

        BankDetailsEntity entity = new BankDetailsEntity();
        entity.setMail("test@example.com");
        entity.setContact(1234567890L);
        entity.setAddress("Test Bank");

        when(bankDetailsService.createBankDetails(dto)).thenReturn(entity);

        // When
        ResponseEntity<BankDetailsEntity> response = contrlr.sa(dto);

        // Then
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getMail());

        verify(bankDetailsService, times(1)).createBankDetails(dto);
    }

    @Test
    void testSaveBankDetails_throwsResourceNotFoundException() {
        // Given
        BankDetailsDto dto = new BankDetailsDto(); // missing required data
        when(bankDetailsService.createBankDetails(dto))
                .thenThrow(new ResourceNotFoundException("Email is required"));

        // When + Then
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> controller.saveBankDetails(dto)
        );

        assertEquals("Email is required", thrown.getMessage());
        verify(bankDetailsService, times(1)).createBankDetails(dto);
    }
}
