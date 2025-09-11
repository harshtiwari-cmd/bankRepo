package com.example.card.constrants.mapper.impl;

import com.example.card.constrants.mapper.CardMapper;
import com.example.card.constrants.model.CardResponse;
import org.springframework.stereotype.Service;

@Service
public class CardMapperImpl implements CardMapper {

    @Override
    public CardResponse toResponse(String status, String message) {
        return new CardResponse(status, message);
    }
}
