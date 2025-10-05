package com.example.card.adapter.api.services.impl;

import com.example.card.domain.dto.FXRateDto;
import com.example.card.domain.dto.FXRateResponseDto;
import com.example.card.constrants.entity.FxRate;
import com.example.card.constrants.mapper.FxRateMapper;
import com.example.card.repository.FxRateRepo;
import com.example.card.adapter.api.services.FXRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FxRateImpl implements FXRateService {

    private final FxRateRepo fxRateRepo;
    private final FxRateMapper fxMapper;

    public FxRateImpl(FxRateRepo fxRateRepo, FxRateMapper fxMapper) {
        this.fxRateRepo = fxRateRepo;
        this.fxMapper = fxMapper;
    }

    @Override
    public FXRateDto createFxRate(FXRateDto dto) {
        log.info("Received request to create FX rate for country code: {}", dto.getCountryCode());

        FxRate fxRate = new FxRate();
        fxRate.setCountryCode(dto.getCountryCode());
        fxRate.setCountryName(dto.getCountryName());
        fxRate.setCurrencyCode(dto.getCurrencyCode());
        fxRate.setSellRate(dto.getSellRate());
        fxRate.setBuyRate(dto.getBuyRate());

        log.debug("FXRate entity before save: {}", fxRate);

        FxRate saved = fxRateRepo.save(fxRate);
        log.info("FX rate saved successfully with ID: {}", saved.getId());

        return new FXRateDto(saved.getCountryCode(), saved.getCountryName(), saved.getCurrencyCode(), saved.getBuyRate(), saved.getSellRate());

    }

    @Override
    public List<FXRateResponseDto> getFx() {
        log.info("Fetching all FX rates");

        List<FXRateResponseDto> fxList = fxRateRepo.findAll()
                .stream()
                .map(fxMapper::toDto)
                .collect(Collectors.toList());

        log.info("Fetched {} FX rate entries", fxList.size());
        log.debug("FX rate list: {}", fxList);

        return fxList;
    }
}
