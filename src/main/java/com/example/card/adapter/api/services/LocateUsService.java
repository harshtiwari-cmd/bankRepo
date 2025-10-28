package com.example.card.adapter.api.services;

import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.domain.dto.BankBranchDTO;
import com.example.card.domain.dto.KioskResponseDTO;
import com.example.card.domain.dto.LocateUsDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface LocateUsService {
    CompletableFuture<Map<String, List<LocateUsDTO>>> fetchAllTypesAsync(String lang);
    String getImageForType(String locatorType) throws IOException;
}


