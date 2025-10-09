//package com.example.card.controller.impl;
//
//import com.example.card.constrants.dto.BankDetailsDto;
//import com.example.card.constrants.entity.BankDetailsEntity;
//import com.example.card.exceptions.ResourceNotFoundException;
//import com.example.card.services.BankDetailsService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//public class BankDetailsControllerImplTest {
//
//    @Mock
//    private BankDetailsService bankDetailsService;
//
//    @InjectMocks
//    private BankDetailsControllerImpl controller;
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testSaveBankDetails_success() {
//
//        BankDetailsDto dto = new BankDetailsDto();
//        dto.setMail("sbi@example.com");
//        dto.setContact(1234567890L);
//
//
//        BankDetailsEntity entity = new BankDetailsEntity();
//        entity.setMail("sbi@example.com");
//        entity.setContact(1234567890L);
//
//
//        when(bankDetailsService.createBankDetails(dto)).thenReturn(entity);
//
//
//        ResponseEntity<String> response = controller.saveBankDetails( dto);
//
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertTrue(response.getBody().contains("bank details saved for bankId :SBI001"));
//
//        verify(bankDetailsService, times(1)).createBankDetails(dto);
//    }
//
//
//    @Test
//    void testSaveBankDetails_throwsResourceNotFoundException() {
//        // Given
//        BankDetailsDto dto = new BankDetailsDto();
//
//
//        when(bankDetailsService.createBankDetails(dto))
//                .thenThrow(new ResourceNotFoundException("Email is required"));
//
//        // When + Then
//        ResourceNotFoundException thrown = assertThrows(
//                ResourceNotFoundException.class,
//                () -> controller.saveBankDetails( dto)
//        );
//
//        assertEquals("Email is required", thrown.getMessage());
//        verify(bankDetailsService, times(1)).createBankDetails(dto);
//    }
//
//}
