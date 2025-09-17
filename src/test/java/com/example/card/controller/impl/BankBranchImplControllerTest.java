package com.example.card.controller.impl;
import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankBranchDTO;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.services.BankBranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankBranchImplControllerTest
{
    @Mock
    private BankBranchService bankBranchService;

    @InjectMocks
    private BankBranchImpl controller;

    private BankBranchDTO sampleBranch;
    private CreateBankBranchDTO sampleCreateDTO;

    @BeforeEach
    void setUp() {
        sampleBranch = new BankBranchDTO();
        sampleBranch.setId(1L);
        sampleBranch.setBankName("Test Bank");
        sampleBranch.setBankBranchName("Main Branch");

        sampleCreateDTO = new CreateBankBranchDTO();
        sampleCreateDTO.setBankName("Test Bank");
        sampleCreateDTO.setBankBranchName("Main Branch");
    }

    @Test
    void getAllBranches_withBranches_returnsOk() {

        when(bankBranchService.getAllBranches()).thenReturn(List.of(sampleBranch));
        ResponseEntity<List<BankBranchDTO>> response = controller.getAllBranches();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Bank", response.getBody().get(0).getBankName());
        verify(bankBranchService, times(1)).getAllBranches();
    }

    @Test
    void getAllBranches_withEmptyList_throwsResourceNotFoundException() {

        when(bankBranchService.getAllBranches()).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> controller.getAllBranches());
        verify(bankBranchService, times(1)).getAllBranches();
    }

    @Test
    void addBranch_withValidRequest_returnsCreated() {
        when(bankBranchService.createBankBranch(sampleCreateDTO)).thenReturn(sampleBranch);
        ResponseEntity<BankBranchDTO> response = controller.addBranch(sampleCreateDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Bank", response.getBody().getBankName());
        verify(bankBranchService, times(1)).createBankBranch(sampleCreateDTO);
    }
}
