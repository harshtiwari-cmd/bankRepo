package com.example.card.services.impl;

import com.example.card.constrants.dto.AtmRequestDto;
import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.entity.AtmEntity;
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

    public AtmServiceImpl(Atm_Repo atmRepo) {
        this.atmRepo = atmRepo;
    }

    @Override
    public AtmResponseDto registerAtm(AtmRequestDto requestDto) {
        log.info("Registering new ATM location with code: {}", requestDto.getCode());

        AtmEntity atmEntity = AtmEntity.builder()
                .arabicName(requestDto.getArabicName())
                .cashDeposit(requestDto.getCashDeposit())
                .cashOut(requestDto.getCashOut())
                .chequeDeposit(requestDto.getChequeDeposit())
                .city(requestDto.getCity())
                .cityInArabic(requestDto.getCityInArabic())
                .code(requestDto.getCode())
                .contactDetails(requestDto.getContactDetails())
                .country(requestDto.getCountry())
                .disablePeople(requestDto.getDisablePeople())
                .fullAddress(requestDto.getFullAddress())
                .fullAddressArb(requestDto.getFullAddressArb())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .onlineLocation(requestDto.getOnlineLocation())
                .timing(requestDto.getTiming())
                .typeLocation(requestDto.getTypeLocation())
                .workingHours(requestDto.getWorkingHours())
                .workingHoursInArb(requestDto.getWorkingHoursInArb())
                .build();

        AtmEntity saved = atmRepo.save(atmEntity);
        log.info("ATM location saved successfully with ID: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public List<AtmResponseDto> getAtm() {
        log.info("Fetching all ATM locations");
        List<AtmEntity> atmEntities = atmRepo.findAll();

        return atmEntities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AtmResponseDto mapToResponse(AtmEntity entity) {
        return AtmResponseDto.builder()
                .id(entity.getId())
                .arabicName(entity.getArabicName())
                .cashDeposit(entity.getCashDeposit())
                .cashOut(entity.getCashOut())
                .chequeDeposit(entity.getChequeDeposit())
                .city(entity.getCity())
                .cityInArabic(entity.getCityInArabic())
                .code(entity.getCode())
                .contactDetails(entity.getContactDetails())
                .country(entity.getCountry())
                .disablePeople(entity.getDisablePeople())
                .fullAddress(entity.getFullAddress())
                .fullAddressArb(entity.getFullAddressArb())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .onlineLocation(entity.getOnlineLocation())
                .timing(entity.getTiming())
                .typeLocation(entity.getTypeLocation())
                .workingHours(entity.getWorkingHours())
                .workingHoursInArb(entity.getWorkingHoursInArb())
                .build();
    }
}
