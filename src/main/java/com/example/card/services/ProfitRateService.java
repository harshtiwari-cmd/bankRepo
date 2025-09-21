package com.example.card.services;

import com.example.card.constrants.dto.ProfitRateRequestDTO;
import com.example.card.constrants.dto.ProfitRateResponseDTO;

import java.util.List;

public interface ProfitRateService {

    List<ProfitRateResponseDTO> getAllProfitRates();

    ProfitRateResponseDTO createProfitRate(ProfitRateRequestDTO profitRate);

}
