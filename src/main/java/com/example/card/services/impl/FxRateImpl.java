package com.example.card.services.impl;

import com.example.card.dto.FXRateDto;
import com.example.card.entity.FxRate;
import com.example.card.repository.FxRateRepo;
import com.example.card.services.FXRateService;
import org.springframework.stereotype.Service;

@Service
public class FxRateImpl implements FXRateService {

    private final FxRateRepo fxRateRepo;

    public FxRateImpl(FxRateRepo fxRateRepo) {
        this.fxRateRepo = fxRateRepo;
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
}
