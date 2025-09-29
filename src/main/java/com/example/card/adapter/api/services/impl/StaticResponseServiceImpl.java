package com.example.card.adapter.api.services.impl;

import com.example.card.domain.model.CardResponse;
import com.example.card.adapter.api.services.StaticResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Slf4j
@Service
public class StaticResponseServiceImpl implements StaticResponseService {
    private CardResponse successResponse;
    private CardResponse failureResponse;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Created ObjectMapper to fetch static response from JSON file");
        try {
            log.info("generating static response for card");
            successResponse = mapper.readValue(
                    new ClassPathResource("static/static-response-success.json").getInputStream(),
                    CardResponse.class);

            failureResponse = mapper.readValue(
                    new ClassPathResource("static/static-response-failure.json").getInputStream(),
                    CardResponse.class);
        } catch (IOException e) {
            log.error("Exception occurred while load static response from JSON file: {}", e);
            throw new RuntimeException("Failed to load static response JSON files", e);
        }
    }

    @Override
    public CardResponse getSuccessResponse() {
        return successResponse;
    }

    @Override
    public CardResponse getFailureResponse() {
        return failureResponse;
    }
}