package com.example.card.adapter.api.services.impl;

import com.example.card.domain.dto.ProfitRateRequestDTO;
import com.example.card.domain.dto.ProfitRateResponseDTO;
import com.example.card.constrants.entity.ProfitRate;
import com.example.card.constrants.mapper.ProfitRateMapper;
import com.example.card.repository.ProfitRateRepository;
import com.example.card.adapter.api.services.ProfitRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

        log.info("Fetching all profit rates from repository");

        List<ProfitRate> profitRates = profitRateRepository.findAll();
        log.info("Successfully {} fetched profit rates",profitRates.size());

        List<ProfitRateResponseDTO> responseDTOS = mapper.toDto(profitRates);
        log.debug("Mapped profitRates to DTO: {}", responseDTOS);
        return responseDTOS;
    }

    @Override
    public ProfitRateResponseDTO createProfitRate(ProfitRateRequestDTO requestDTO) {

        log.info("Starting creation new profit rate");

        ProfitRate entity = mapper.toEntity(requestDTO);
        log.debug("Mapped DTO to profitRate: {}", entity);

        ProfitRate saved = profitRateRepository.save(entity);
        log.info("Successfully saved profitRate: {}", saved);

        ProfitRateResponseDTO responseDTO = mapper.toDto(saved);
        log.debug("Mapped ProfitRate to DTO: {}", responseDTO);

        return responseDTO;
    }

}
