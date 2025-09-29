package com.example.card.adapter.api.services;

import com.example.card.domain.dto.ProfitRateRequestDTO;
import com.example.card.domain.dto.ProfitRateResponseDTO;

import java.util.List;

public interface ProfitRateService {

    List<ProfitRateResponseDTO> getAllProfitRates();

    ProfitRateResponseDTO createProfitRate(ProfitRateRequestDTO profitRate);

}
