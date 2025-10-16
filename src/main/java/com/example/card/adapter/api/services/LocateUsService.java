package com.example.card.adapter.api.services;

import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.domain.dto.BankBranchDTO;
import com.example.card.domain.dto.KioskResponseDTO;
import com.example.card.domain.dto.LocateUsDTO;

import java.util.List;

public interface LocateUsService {
    List<BankBranchDTO> fetchBranches();
    List<AtmResponseDto> fetchAtms();
    List<KioskResponseDTO> fetchKiosks();

    // unified variant
    List<LocateUsDTO> fetchAllUnified();
    List<LocateUsDTO> fetchByType(String locatorType, String lang);
}


