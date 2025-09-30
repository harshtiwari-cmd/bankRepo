package com.example.card.adapter.api.services;

import com.example.card.domain.dto.BankDetailsDto;
import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.constrants.entity.BankDetailsEntity;

public interface BankDetailsService {
    BankDetailsEntity createBankDetails(BankDetailsDto dto);
    BankDetailsResponseDto getbankDetails();
}
