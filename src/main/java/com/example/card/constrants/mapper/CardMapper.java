package com.example.card.constrants.mapper;

import com.example.card.constrants.model.CardResponse;

public interface CardMapper {
    CardResponse toResponse(String status, String message);
}
