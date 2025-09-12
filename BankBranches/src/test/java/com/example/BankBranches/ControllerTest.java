//package com.example.BankBranches;
//
//import com.example.BankBranches.Controller.BankBranchController;
//import com.example.BankBranches.DTO.BankBranchDTO;
//import com.example.BankBranches.Entities.BankBranch;
//import com.example.BankBranches.Services.BankBranchMethodsImplementation;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//@WebMvcTest(BankBranchController.class)
//public class ControllerTest
//{
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private BankBranchMethodsImplementation bankBranchMethodsImplementation;
//    private String readJsonFile(String fileName) throws Exception
//    {
//        return new String(Files.readAllBytes(Paths.get("src/test/resources/CreateBranch.json")));
//    }
//    @Test
//    void testAddBranch() throws Exception
//    {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String createdJson = readJsonFile("CreateBranch.json");
//        BankBranchDTO dto = objectMapper.readValue(createdJson,BankBranchDTO.class);
//        BankBranch expected = objectMapper.readValue(createdJson,BankBranch.class);
//        Mockito.when(bankBranchMethodsImplementation.saveBankBranch(dto)).thenReturn(expected);
//        mockMvc.perform(post("/api/branches/addbranch")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(createdJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.bankName").value("Sample Bank"))
//                .andExpect(jsonPath("$.bankBranchName").value("Main Branch"))
//                .andExpect(jsonPath("$.branchNumber").value("123456789"));
//    }
//}
