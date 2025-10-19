package com.example.card.adapter.api.services.impl;

import com.example.card.adapter.api.services.LocateUsService;
import com.example.card.constrants.entity.LocateUsImages;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<Map<String, List<LocateUsDTO>>> fetchAllTypesAsync(String lang) {
        log.info("Mock fetchAllTypesAsync called for language: {}", lang);
        
        // Fetch all types using the existing fetchByType method
        List<LocateUsDTO> branches = fetchByType("BRANCH", lang);
        List<LocateUsDTO> atms = fetchByType("ATM", lang);
        List<LocateUsDTO> kiosks = fetchByType("KIOSK", lang);
        
        Map<String, List<LocateUsDTO>> result = new HashMap<>();
        result.put("branches", branches);
        result.put("atms", atms);
        result.put("kiosks", kiosks);
        
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public String getImageForType(String locatorType) {
        if (locatorType == null || locatorType.isEmpty()) {
            throw new IllegalArgumentException("Locator type must not be null or empty");
        }

        try {
            ClassPathResource resource = new ClassPathResource("JSON/locate-us-images.json");

            List<LocateUsImages> usImages = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<LocateUsImages>>() {}
            );

            for (LocateUsImages img : usImages) {
                if (locatorType.equalsIgnoreCase(img.getLocatorType())) {
                    return img.getImage();
                }
            }
        } catch (IOException e) {
            // Log the error or handle it as needed
            System.err.println("Error reading image data: " + e.getMessage());
        }

        return ""; // or return a default image path if preferred
    }

    private List<LocateUsDTO> fetchByType(String locatorType, String lang) {
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