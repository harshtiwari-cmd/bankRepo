package com.example.card.adapter.api.controller;

import com.example.card.adapter.api.services.BankDetailsService;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.domain.dto.BankDetailsNewRequestDto;
import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.domain.dto.FollowUsItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankDetailsController.class)
class BankDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BankDetailsService bankDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private BankDetailsResponseDto responseDto;

    @BeforeEach
    public void setUp() {
        ArrayList<FollowUsItemDto> list = new ArrayList<>();

        FollowUsItemDto dto1 = FollowUsItemDto.builder()
                .instaUrlAR("https://www.instagram.com/dukhanbank/")
                .instaUrlEN("https://www.instagram.com/dukhanbank/?hl=ar")
                .nameEn("Instagram")
                .displayOrder(1).build();

        FollowUsItemDto dto2 = FollowUsItemDto.builder()
                .instaUrlAR("https://www.snapchat.com/add/dukhanbank")
                .instaUrlEN("https://www.snapchat.com/add/dukhanbank")
                .nameEn("snapchat")
                .displayOrder(2).build();

        list.add(dto1);
        list.add(dto2);

         responseDto = BankDetailsResponseDto.builder()
                .nameEn("Dukhan Bank")
                .nameAr("بنك دخان")
                .mail("info.dukhanbank.com")
                .contact(4444444L)
                .internationalContact("+97444444")
                .followUs(list)
                .displayOrder(0)
                .build();
    }

    @Test
    public void saveBankDetailsNew_Success_ReturnsOkResponse() throws Exception {
        BankDetailsNewRequestDto requestDto = BankDetailsNewRequestDto.builder()
                .nameEn("Dukhan Bank")
                .mail("info.dukhanbank.com")
                .contact(12345678L)
                .internationalContact("+9712345678")
                .displayOrder(1)
                .build();

        BankDetailsEntity entity = new BankDetailsEntity();
        entity.setId(10L);

        Mockito.when(bankDetailsService.saveBankDetailsNew(requestDto)).thenReturn(entity);

        mockMvc.perform(post("/bank-details/save-new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isOk())
                  .andExpect(content().string("Bank detail saved with id: 10"));

        verify(bankDetailsService, times(1)).saveBankDetailsNew(Mockito.any());
    }

    @Test
    public void saveBankDetailsNew_ServiceThrowsException_ReturnsInternalServerError() throws Exception {

        BankDetailsNewRequestDto requestDto = BankDetailsNewRequestDto.builder()
                .nameEn("Dukhan Bank")
                .mail("info.dukhanbank.com")
                .contact(12345678L)
                .internationalContact("+9712345678")
                .displayOrder(1)
                .build();

        BankDetailsEntity entity = new BankDetailsEntity();
        entity.setId(10L);

        Mockito.when(bankDetailsService.saveBankDetailsNew(requestDto))
                .thenThrow(new RuntimeException("DB down"));

        mockMvc.perform(post("/bank-details/save-new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to save bank details"));

        verify(bankDetailsService, times(1)).saveBankDetailsNew(Mockito.any());
    }

    @Test
     void getBankDetails_Success_ReturnsOkResponse() throws Exception {

        Mockito.when(bankDetailsService.getBankDetails()).thenReturn(responseDto);

        mockMvc.perform(get("/bank-details") // Replace with actual endpoint path
                        .header("UNIT", "test-unit")
                        .header("CHANNEL", "web")
                        .header("ACCEPT_LANGUAGE", "en")
                        .header("SERVICEID", "service123")
                        .header("SCREENID", "screen123")
                        .header("MODULE_ID", "module123")
                        .header("SUB_MODULE_ID", "submodule123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
                .andExpect(jsonPath("$.data.nameEn").value("Dukhan Bank"))
                .andExpect(jsonPath("$.data.displayOrder").value(0))
                .andExpect(jsonPath("$.data.followUs.length()").value(2));

        verify(bankDetailsService, times(1)).getBankDetails();

    }

    @Test
     void getBankDetails_NoHeadersProvided_ReturnsSuccess() throws Exception {
        Mockito.when(bankDetailsService.getBankDetails()).thenReturn(responseDto);

        mockMvc.perform(get("/bank-details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
                .andExpect(jsonPath("$.data.nameEn").value("Dukhan Bank"));

        verify(bankDetailsService, times(1)).getBankDetails();
    }

    @Test
     void getBankDetails_PartialHeadersProvided_ReturnsSuccess() throws Exception {
        Mockito.when(bankDetailsService.getBankDetails()).thenReturn(responseDto);

        mockMvc.perform(get("/bank-details")
                        .header("UNIT", "test-unit")
                        .header("ACCEPT_LANGUAGE", "ar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.data.nameEn").value("Dukhan Bank"));

        verify(bankDetailsService, times(1)).getBankDetails();
    }

    @Test
     void getBankDetails_ServiceThrowsException_ReturnsInternalServerError() throws Exception {
        Mockito.when(bankDetailsService.getBankDetails()).thenThrow(new RuntimeException("Database is down"));

        mockMvc.perform(get("/bank-details")
                        .header("UNIT", "test-unit"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status.code").value("G-00001"))
                .andExpect(jsonPath("$.status.description").value("Internal Server ERROR"))
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(bankDetailsService, times(1)).getBankDetails();
    }



}