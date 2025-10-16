package com.example.card.adapter.api.controller;

import com.example.card.adapter.api.services.BankDetailsService;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.domain.dto.BankDetailsNewRequestDto;
import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.domain.model.CardBinAllWrapper;
import com.example.card.domain.model.DeviceInfo;
import com.example.card.domain.model.SocialMedia;
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

    private CardBinAllWrapper cardBinAllWrapper;

    private BankDetailsResponseDto responseDto;

    @BeforeEach
    public void setUp() {

        DeviceInfo deviceinfo = DeviceInfo
                .builder()
                .deviceId("DEVICE123")
                .ipAddress("192.168.1.1")
                .vendorId("VENDOR123")
                .osVersion("1.1.0")
                .osType("Android")
                .appVersion("2.1.0")
                .endToEndId("E2E123")
                .build();

        cardBinAllWrapper = new CardBinAllWrapper();

        cardBinAllWrapper.setRequestInfo(null);
        cardBinAllWrapper.setDeviceInfo(deviceinfo);


        ArrayList<SocialMedia> list = new ArrayList<>();

        SocialMedia sm1 = SocialMedia.builder()
                .url("https://www.instagram.com/dukhanbank/")
                .name("Instagram")
                .displayOrder(1).build();

        SocialMedia sm2 = SocialMedia.builder()
                .url("https://www.snapchat.com/add/dukhanbank")
                .name("snapchat")
                .displayOrder(2).build();

        list.add(sm1);
        list.add(sm2);

         responseDto = BankDetailsResponseDto.builder()
                .mail("info.dukhanbank.com")
                .contact(4444444L)
                .internationalContact("+97444444")
                .followUs(list)
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
                        .header("unit", "test-unit")
                        .header("channel", "web")
                        .header("accept-language", "en")
                        .header("serviceId", "service123")
                        .header("screenId", "screen123")
                        .header("moduleId", "module123")
                        .header("subModuleId", "submodule123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
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
                        .header("unit", "test-unit")
                        .header("channel", "web")
                        .header("accept-language", "en")
                        .header("serviceId", "service123")
                        .header("screenId", "screen123")
                        .header("moduleId", "module123")
                        .header("subModuleId", "submodule123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to save bank details"));

        verify(bankDetailsService, times(1)).saveBankDetailsNew(Mockito.any());
    }

    @Test
     void getBankDetails_Success_ReturnsOkResponse() throws Exception {

        Mockito.when(bankDetailsService.getBankDetails("en")).thenReturn(responseDto);

        mockMvc.perform(post("/bank-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "test-unit")
                        .header("channel", "web")
                        .header("accept-language", "en")
                        .header("serviceId", "service123")
                        .header("screenId", "screen123")
                        .header("moduleId", "module123")
                        .header("subModuleId", "submodule123")
                        .content(objectMapper.writeValueAsString(cardBinAllWrapper))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("000000"))
                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
                .andExpect(jsonPath("$.data.mail").value("info.dukhanbank.com"))
                .andExpect(jsonPath("$.data.followUs[0].name").value("Instagram"));

        verify(bankDetailsService, times(1)).getBankDetails("en");

    }

    @Test
     void getBankDetails_PartialHeadersProvided_ReturnsBadRequest() throws Exception {
        Mockito.when(bankDetailsService.getBankDetails("")).thenReturn(responseDto);

        mockMvc.perform(post("/bank-details")
                       // .header("unit", "test-unit") - not passing header
                        .header("channel", "web")
                        .header("accept-language", "en")
                        .header("serviceId", "service123")
                        .header("screenId", "screen123")
                        .header("moduleId", "module123")
                        .header("subModuleId", "submodule123")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Required header 'unit' is not present."))
                .andExpect(jsonPath("$.title").value("Bad Request"));

    }

    @Test
     void getBankDetails_ServiceThrowsException_ReturnsInternalServerError() throws Exception {
        Mockito.when(bankDetailsService.getBankDetails("en")).thenThrow(new RuntimeException("Database is down"));

        mockMvc.perform(post("/bank-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "test-unit")
                        .header("channel", "web")
                        .header("accept-language", "en")
                        .header("serviceId", "service123")
                        .header("screenId", "screen123")
                        .header("moduleId", "module123")
                        .header("subModuleId", "submodule123")
                        .content(objectMapper.writeValueAsString(cardBinAllWrapper))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status.code").value("G-00001"))
                .andExpect(jsonPath("$.status.description").value("Internal Server ERROR"))
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(bankDetailsService, times(1)).getBankDetails("en");
    }

    @Test
    void getBankDetails_shouldReturnException_whenLangIsNotSupported() throws Exception {

        mockMvc.perform(post("/bank-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("unit", "PRD")
                        .header("channel", "MB")
                        .header("accept-language", "hindi") // - trying with hindi
                        .header("serviceId", "LOGIN")
                        .header("screenId", "SC_01")
                        .header("moduleId", "MI_01")
                        .header("subModuleId", "SMI_01")
                        .content(objectMapper.writeValueAsString(cardBinAllWrapper))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status.code").value("G-00000"))
                .andExpect(jsonPath("$.status.description").value("Unsupported language. Use 'ar' or 'en'."))
                .andExpect(jsonPath("$.data").doesNotExist());
    }


}