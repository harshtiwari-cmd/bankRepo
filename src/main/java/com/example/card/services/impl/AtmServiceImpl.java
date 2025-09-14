package com.example.card.services.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.entity.AtmEntity;
import com.example.card.repository.Atm_Repo;
import com.example.card.services.AtmService;
import org.springframework.stereotype.Service;

@Service
public class AtmServiceImpl implements AtmService {

    private final Atm_Repo atmRepo;

    public AtmServiceImpl(Atm_Repo atmRepo) {
        this.atmRepo = atmRepo;
    }



    @Override
    public AtmResponseDto registerAtm(AtmRequestDto requestDto) {
        AtmEntity atmEntity=     AtmEntity.builder().
                atmId(requestDto.getAtmId())
                .branchId(requestDto.getBranchId())
                .siteName(requestDto.getSiteName())
                .streetName(requestDto.getStreetName())
                .townName(requestDto.getTownName())
                .country(requestDto.getCountry())
                .postCode(requestDto.getPostCode())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .supportedLanguages(String.join(",",requestDto.getSupportedLanguages()))
                .atmServices(requestDto.getAtmServices())
                .supportedCurrencies(String.join(",",    requestDto.getSupportedCurrencies()))
                .minimumPossibleAmount(requestDto.getMinimumPossibleAmount())
                .openTime(requestDto.getOpenTime())
                .build();
        AtmEntity saved= atmRepo.save(atmEntity);
        System.out.println("Test log from Ashish to trigger PR");


        return AtmResponseDto.builder()
                .id(saved.getId())
                .atmId(saved.getAtmId())
                .branchId(saved.getBranchId())
                .siteName(saved.getSiteName())
                .townName(saved.getTownName())
                .country(saved.getCountry())
                .openTime(saved.getOpenTime()).build();

    }


}
