package com.example.card.services;

import com.example.card.dto.BankDetailsDto;
import com.example.card.dto.KioskResponseDTO;
import com.example.card.entity.BankDetailsEntity;

import java.util.List;

public interface BankDetailsService {
    BankDetailsEntity createBankDetails(BankDetailsDto dto);
    List<BankDetailsDto> getbankDetails(String bankId);
}
