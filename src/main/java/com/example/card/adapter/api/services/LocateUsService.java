package com.example.card.adapter.api.services;

import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.domain.dto.BankBranchDTO;
import com.example.card.domain.dto.KioskResponseDTO;
import com.example.card.domain.dto.LocateUsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface LocateUsService {
    List<BankBranchDTO> fetchBranches();
    List<AtmResponseDto> fetchAtms();
    List<KioskResponseDTO> fetchKiosks();

    // unified variant
    List<LocateUsDTO> fetchAllUnified();
    CompletableFuture<List<LocateUsDTO>> fetchByTypeAsync(String locatorType, String lang);
}


