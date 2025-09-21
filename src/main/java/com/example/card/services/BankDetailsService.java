package com.example.card.services;

import com.example.card.constrants.dto.BankDetailsDto;
import com.example.card.constrants.dto.BankDetailsResponseDto;
import com.example.card.constrants.entity.BankDetailsEntity;

import java.util.List;

public interface BankDetailsService {
    BankDetailsEntity createBankDetails(BankDetailsDto dto);
    BankDetailsResponseDto getbankDetails();
}
