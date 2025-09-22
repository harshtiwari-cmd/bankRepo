package com.example.card.services.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.dto.CoordinatesDTO;
import com.example.card.constrants.mapper.AtmMapper;
import com.example.card.constrants.entity.AtmEntity;
import com.example.card.constrants.model.Coordinates;
import com.example.card.repository.Atm_Repo;
import com.example.card.services.AtmService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtmServiceImpl implements AtmService {

    private final Atm_Repo atmRepo;
    private final AtmMapper atmMapper;

    public AtmServiceImpl(Atm_Repo atmRepo,AtmMapper atmMapper) {

        this.atmRepo = atmRepo;
        this.atmMapper = atmMapper;
    }



    @Override
    public AtmResponseDto registerAtm(AtmRequestDto requestDto) {
        Coordinates coordinates = requestDto.getCoordinates();
        AtmEntity atmEntity = AtmEntity.builder().
                atmId(requestDto.getAtmId())
                .branchId(requestDto.getBranchId())
                .siteName(requestDto.getSiteName())
                .streetName(requestDto.getStreetName())
                .townName(requestDto.getTownName())
                .country(requestDto.getCountry())
                .postCode(requestDto.getPostCode())
                .coordinates(coordinates)
                .supportedLanguages(String.join(",", requestDto.getSupportedLanguages()))
                .atmServices(requestDto.getAtmServices())
                .supportedCurrencies(String.join(",", requestDto.getSupportedCurrencies()))
                .minimumPossibleAmount(requestDto.getMinimumPossibleAmount())
                .openTime(requestDto.getOpenTime())
                .build();
        AtmEntity saved = atmRepo.save(atmEntity);

        return AtmResponseDto.builder()
                .id(saved.getId())
                .atmId(saved.getAtmId())
                .branchId(saved.getBranchId())
                .siteName(saved.getSiteName())
                .townName(saved.getTownName())
                .country(saved.getCountry())
                .openTime(saved.getOpenTime())
                .coordinates(
                        CoordinatesDTO.builder()
                                .latitude(saved.getCoordinates().getLatitude())
                                .longitude(saved.getCoordinates().getLongitude())
                                .build()
                )
                .build();
    }

    @Override
    public List<AtmResponseDto> getAtm() {
        return atmRepo.findAll()
                .stream()
                .map(atmMapper::toDto)
                .collect(Collectors.toList());
    }
}
