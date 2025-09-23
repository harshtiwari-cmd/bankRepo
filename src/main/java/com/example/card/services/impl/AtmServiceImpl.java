package com.example.card.services.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.dto.CoordinatesDTO;
import com.example.card.constrants.mapper.AtmMapper;
import com.example.card.constrants.entity.AtmEntity;
import com.example.card.constrants.model.Coordinates;
import com.example.card.repository.Atm_Repo;
import com.example.card.services.AtmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
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
        log.info("Registering new ATM with ATM ID: {}", requestDto.getAtmId());
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
        log.debug("ATM entity built: {}", atmEntity);

        AtmEntity   saved= atmRepo.save(atmEntity);
        log.info("ATM saved successfully with ID: {}", saved.getId());



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
        log.info("Fetching all registered ATMs");

        List<AtmResponseDto> atmList = List.of();
        try {
            atmList = atmRepo.findAll()
                    .stream()
                    .map(atmMapper::toDto)
                    .collect(Collectors.toList());
            log.info("Fetched {} ATMs", atmList.size());
        } catch (Exception e) {
            log.error("Failed to fetch ATMs. Error: {}", e.getMessage(), e);
        }

        log.debug("ATM list retrieved: {}", atmList);
        return atmList;
    }

}
