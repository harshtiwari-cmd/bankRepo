package com.example.card.services.impl;

import com.example.card.constrants.dto.FXRateDto;
import com.example.card.constrants.dto.FXRateResponseDto;
import com.example.card.constrants.entity.FxRate;
import com.example.card.constrants.mapper.FxRateMapper;
import com.example.card.repository.FxRateRepo;
import com.example.card.services.FXRateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FxRateImpl implements FXRateService {

    private final FxRateRepo fxRateRepo;
    private final FxRateMapper fxMapper;
    public FxRateImpl(FxRateRepo fxRateRepo,FxRateMapper fxMapper) {
        this.fxRateRepo = fxRateRepo;
        this.fxMapper = fxMapper;
    }

    @Override
    public FXRateDto createFxRate(FXRateDto dto) {
        FxRate fxRate=new FxRate();
        fxRate.setCountryCode(dto.getCountryCode());
        fxRate.setCountryName(dto.getCountryName());
        fxRate.setCurrencyCode(dto.getCurrencyCode());
        fxRate.setSellRate(dto.getSellRate());
        fxRate.setBuyRate(dto.getBuyRate());
        FxRate saved = fxRateRepo.save(fxRate);
        return new FXRateDto(saved.getCountryCode(), saved.getCountryName(), saved.getCurrencyCode(), saved.getBuyRate(),saved.getSellRate());

    }
    @Override
    public List<FXRateResponseDto> getFx() {
        return fxRateRepo.findAll()
                .stream()
                .map(fxMapper::toDto)
                .collect(Collectors.toList());
    }
}
