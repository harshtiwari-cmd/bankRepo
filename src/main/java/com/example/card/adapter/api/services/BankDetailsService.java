package com.example.card.adapter.api.services;

import com.example.card.domain.dto.BankDetailsNewRequestDto;
import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface BankDetailsService {

    BankDetailsResponseDto getBankDetails(String lang) throws JsonProcessingException;

    BankDetailsEntity saveBankDetailsNew(BankDetailsNewRequestDto dto);

}
