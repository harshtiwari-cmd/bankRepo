package com.example.card.services;

import com.example.card.constrants.model.CardResponse;

public interface StaticResponseService {
    CardResponse getSuccessResponse();
    CardResponse getFailureResponse();
}
