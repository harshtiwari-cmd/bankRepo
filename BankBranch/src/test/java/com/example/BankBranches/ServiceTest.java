package com.example.BankBranches;

import com.example.BankBranches.DTO.BankBranchDTO;
import com.example.BankBranches.Entities.BankBranch;
import com.example.BankBranches.Repositories.BankBranchRepository;
import com.example.BankBranches.Services.BankBranchMethodsImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.nio.file.Files;
import java.nio.file.Paths;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private BankBranchMethodsImplementation bankBranchMethodsImplementation;

    @MockitoBean
    private BankBranchRepository bankBranchRepository;

    @Test
    void testAddBankBranch() throws Exception {
        // arrange
        ObjectMapper objectMapper = new ObjectMapper();
        String createdJson = new String(Files.readAllBytes(Paths.get("src/test/resources/CreateBranch.json")));
        BankBranchDTO dto = objectMapper.readValue(createdJson, BankBranchDTO.class);
        BankBranch expectedBranch = objectMapper.readValue(createdJson, BankBranch.class);

        when(bankBranchRepository.save(any(BankBranch.class))).thenReturn(expectedBranch);

        // act
        BankBranch savedBankBranch = bankBranchMethodsImplementation.saveBankBranch(dto);

        // assert
        Assertions.assertEquals(expectedBranch.getBankName(), savedBankBranch.getBankName());
    }


    @Test
    void testAddBankBranchWithNullOptionalFields() throws Exception {
        // arrange
        ObjectMapper objectMapper = new ObjectMapper();
        String createdJson = new String(Files.readAllBytes(Paths.get("src/test/resources/BranchDataWithNulls.json")));
        BankBranchDTO dto = objectMapper.readValue(createdJson, BankBranchDTO.class);

        BankBranch expectedEntity = objectMapper.readValue(createdJson, BankBranch.class);

        when(bankBranchRepository.save(any(BankBranch.class))).thenReturn(expectedEntity);

        // act
        BankBranch result = bankBranchMethodsImplementation.saveBankBranch(dto);

        // assert
        Assertions.assertEquals("Edge Bank", result.getBankName());
        Assertions.assertEquals("Null Branch", result.getBankBranchName());
        Assertions.assertTrue(result.getWeeklyHolidayList().isEmpty(), "Weekly holidays should be empty");
        Assertions.assertTrue(result.getHolidayCalendar().isEmpty(), "Holiday calendar should be empty");
        Assertions.assertNull(result.getCoordinates(), "Coordinates should be null");
    }
}
