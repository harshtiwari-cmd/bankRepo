//package com.example.BankBranches;
//
//import com.example.BankBranches.DTO.BankBranchDTO;
//import com.example.BankBranches.Entities.BankBranch;
//import com.example.BankBranches.Repositories.BankBranchRepository;
//import com.example.BankBranches.Services.BankBranchMethodsImplementation;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//public class ServiceTest
//{
//    @Autowired
//    BankBranchMethodsImplementation bankBranchMethodsImplementation;
//
//    @MockitoBean
//    private BankBranchRepository bankBranchRepository;
//
//    private String readJsonFile(String fileName) throws Exception
//    {
//        return new String(Files.readAllBytes(Paths.get("src/test/resources/CreateBranch.json")));
//    }
//
//    @Test
//    void testAddBankBranch() throws Exception
//    {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String createdJson = readJsonFile("CreateBranch.json");
//        BankBranchDTO dto = objectMapper.readValue(createdJson,BankBranchDTO.class);
//        BankBranch savedBankBranch = bankBranchMethodsImplementation.saveBankBranch(dto);
//        BankBranch expectedBranch = objectMapper.readValue(createdJson,BankBranch.class);
//        Assertions.assertEquals(expectedBranch,savedBankBranch);
//    }
//
//}
