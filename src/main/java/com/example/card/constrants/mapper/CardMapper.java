package com.example.card.constrants.mapper;

import com.example.card.domain.model.CardResponse;

public interface CardMapper {
    CardResponse toResponse(String status, String message);
}
