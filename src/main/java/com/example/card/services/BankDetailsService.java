package com.example.card.services;

import com.example.card.dto.BankDetailsDto;
import com.example.card.dto.BankDetailsResponseDto;
import com.example.card.entity.BankDetailsEntity;

import java.util.List;

public interface BankDetailsService {
    BankDetailsEntity createBankDetails(BankDetailsDto dto);
    List<BankDetailsResponseDto> getbankDetails(String bankId);
}
