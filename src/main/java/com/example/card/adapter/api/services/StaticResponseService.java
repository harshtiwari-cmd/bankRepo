package com.example.card.adapter.api.services;

import com.example.card.domain.model.CardResponse;

public interface StaticResponseService {
    CardResponse getSuccessResponse();
    CardResponse getFailureResponse();
}
