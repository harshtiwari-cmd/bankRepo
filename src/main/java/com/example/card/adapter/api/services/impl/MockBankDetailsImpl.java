package com.example.card.adapter.api.services.impl;

import com.example.card.adapter.api.services.BankDetailsService;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.domain.dto.BankDetailsNewRequestDto;
import com.example.card.domain.dto.BankDetailsResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@ConditionalOnProperty(name = "mock.enabled", havingValue = "true")
public class MockBankDetailsImpl implements BankDetailsService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BankDetailsResponseDto getBankDetails(String lang) {
        String language = "ar".equalsIgnoreCase(lang) ? "AR" : "EN";
        String fileName = "JSON/mock-" + language + "-bank-details.json";

        log.info("Loading mock bank details for language: {}", language);

        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            BankDetailsResponseDto response = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<>() {}
            );

            log.debug("Successfully loaded mock bank details: {}", response);
            return response;

        } catch (IOException e) {
            log.error("Failed to load mock bank details from file: {}", fileName, e);
        }

        return null;
    }

    @Override
    public BankDetailsEntity saveBankDetailsNew(BankDetailsNewRequestDto dto) {
        log.warn("Mock implementation: saveBankDetailsNew is not supported.");
        return null;
    }
}