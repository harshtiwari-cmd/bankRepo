package com.example.card.adapter.api.services.impl;

import com.example.card.adapter.api.services.LocateUsService;
import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.domain.dto.BankBranchDTO;
import com.example.card.domain.dto.KioskResponseDTO;
import com.example.card.domain.dto.LocateUsDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@ConditionalOnProperty(name = "mock.enabled", havingValue = "true")
public class MockLocateUsServiceImpl implements LocateUsService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<BankBranchDTO> fetchBranches() {
        log.info("Mock fetchBranches called — returning empty list");
        return List.of();
    }

    @Override
    public List<AtmResponseDto> fetchAtms() {
        log.info("Mock fetchAtms called — returning empty list");
        return List.of();
    }

    @Override
    public List<KioskResponseDTO> fetchKiosks() {
        log.info("Mock fetchKiosks called — returning empty list");
        return List.of();
    }

    @Override
    public List<LocateUsDTO> fetchAllUnified() {
        log.info("Mock fetchAllUnified called — returning empty list");
        return List.of();
    }

    @Override
    public List<LocateUsDTO> fetchByType(String locatorType, String lang) {
        String type = locatorType != null ? locatorType.toUpperCase() : "";
        String language = "ar".equalsIgnoreCase(lang) ? "AR" : "EN";

        String fileName = switch (type) {
            case "BRANCH" -> "mock-%s-branch.json".formatted(language);
            case "ATM" -> "mock-%s-atm.json".formatted(language);
            case "KIOSK" -> "mock-%s-kiosk.json".formatted(language);
            default -> null;
        };

        if (fileName == null) {
            log.warn("Unsupported locator type: '{}'. Returning empty list.", locatorType);
            return List.of();
        }

        log.info("Loading mock data for type: '{}' and language: '{}'", type, language);

        try {
            ClassPathResource resource = new ClassPathResource("JSON/" + fileName);
            List<LocateUsDTO> data = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
            log.debug("Successfully loaded {} items from {}", data.size(), fileName);
            return data;
        } catch (IOException e) {
            log.error("Failed to load mock data from file: {}", fileName, e);
            return List.of();
        }
    }
}