//package com.example.card.adapter.api.controller;
//
//import com.example.card.adapter.api.services.ServiceBookingService;
//import com.example.card.domain.dto.ServiceBookingRequestDTO;
//import com.example.card.domain.dto.ServiceBookingResponseDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ServiceBookingController.class)
//public class ServiceBookingControllerTest {
//
//    @MockitoBean
//    private ServiceBookingService service;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private ServiceBookingRequestDTO requestDTO;
//
//    private ServiceBookingResponseDTO responseDTO;
//
//    @BeforeEach
//    void setup() {
//        requestDTO = ServiceBookingRequestDTO.builder()
//                .serviceName("Account Summary")
//                .description("Display customer's account balance and recent transactions")
//                .isActive(true)
//                .build();
//
//        responseDTO = ServiceBookingResponseDTO.builder()
//                .serviceName("Account Summary")
//                .description("Display customer's account balance and recent transactions")
//                .screenId("SCR001")
//                .isActive(true)
//                .build();
//    }
//
//    @Test
//    void createService_ShouldReturnCreated() throws Exception {
//
//        Mockito.when(service.createService(requestDTO, "SCR001") ).thenReturn(responseDTO);
//
//        mockMvc.perform(post("/service-booking")
//                        .param("screen", "SCR001")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("unit", "PRD")
//                        .header("channel", "MB")
//                        .header("accept-language", "en")
//                        .header("serviceId", "LOGIN")
//                        .header("screenId", "SC_01")
//                        .header("moduleId", "MI_01")
//                        .header("subModuleId", "SMI_01")
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.serviceName").value("Account Summary"))
//                .andExpect(jsonPath("$.description").value("Display customer's account balance and recent transactions"))
//                .andExpect(jsonPath("$.screenId").value("SCR001"))
//                .andExpect(jsonPath("$.active").value(true));
//    }
//
//    @Test
//    void getAllServicesName_ShouldReturnSuccessWhenDataExists() throws Exception {
//
//        Mockito.when(service.getServiceByScreenId()).thenReturn(Collections.singletonList(responseDTO));
//        mockMvc.perform(get("/service-booking")
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status.code").value("000000"))
//                .andExpect(jsonPath("$.status.description").value("SUCCESS"))
//                .andExpect(jsonPath("$.data[0]").value("1 - Account Summary"));
//    }
//
//    @Test
//    void getAllServicesName_ShouldReturnNoDataFoundWhenEmpty() throws Exception {
//
//        Mockito.when(service.getServiceByScreenId()).thenReturn(Collections.emptyList());
//        mockMvc.perform(get("/service-booking"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status.code").value("000404"))
//                .andExpect(jsonPath("$.status.description").value("No Data Found"))
//                .andExpect(jsonPath("$.data").isEmpty());
//    }
//
//}
