package com.example.card.adapter.api.services;

import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.domain.dto.BankBranchDTO;
import com.example.card.domain.dto.KioskResponseDTO;
import com.example.card.domain.dto.LocateUsDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface LocateUsService {
    List<BankBranchDTO> fetchBranches();
    List<AtmResponseDto> fetchAtms();
    List<KioskResponseDTO> fetchKiosks();
    List<LocateUsDTO> fetchAllUnified();
    CompletableFuture<Map<String, List<LocateUsDTO>>> fetchAllTypesAsync(String lang);
}


