package com.example.card.services.impl;

import com.example.card.constrants.model.CardResponse;
import com.example.card.services.StaticResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class StaticResponseServiceImpl implements StaticResponseService {
    private CardResponse successResponse;
    private CardResponse failureResponse;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            successResponse = mapper.readValue(
                    new ClassPathResource("static/static-response-success.json").getInputStream(),
                    CardResponse.class);

            failureResponse = mapper.readValue(
                    new ClassPathResource("static/static-response-failure.json").getInputStream(),
                    CardResponse.class);
        } catch (IOException e) {
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