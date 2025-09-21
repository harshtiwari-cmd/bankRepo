package com.example.card.services.impl;

import com.example.card.constrants.dto.ProfitRateRequestDTO;
import com.example.card.constrants.dto.ProfitRateResponseDTO;
import com.example.card.constrants.entity.ProfitRate;
import com.example.card.constrants.mapper.ProfitRateMapper;
import com.example.card.repository.ProfitRateRepository;
import com.example.card.services.ProfitRateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfitRateServiceImpl implements ProfitRateService {

    private final ProfitRateRepository profitRateRepository;

    private final ProfitRateMapper mapper;

    public ProfitRateServiceImpl(ProfitRateRepository profitRateRepository, ProfitRateMapper mapper) {
        this.profitRateRepository = profitRateRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProfitRateResponseDTO> getAllProfitRates() {

        List<ProfitRate> profitRates = profitRateRepository.findAll();

        return mapper.toDto(profitRates);
    }

    @Override
    public ProfitRateResponseDTO createProfitRate(ProfitRateRequestDTO requestDTO) {

        ProfitRate entity = mapper.toEntity(requestDTO);

        ProfitRate saved = profitRateRepository.save(entity);

        return mapper.toDto(saved);

    }
}
