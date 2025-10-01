package com.example.card.adapter.api.services.impl;

import com.example.card.constrants.mapper.KioskMapper;
import com.example.card.domain.model.Kiosk;
import com.example.card.domain.dto.KioskRequestDTO;
import com.example.card.domain.dto.KioskResponseDTO;
import com.example.card.exceptions.BusinessException;
import com.example.card.repository.KioskRepository;
import com.example.card.adapter.api.services.KioskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class KioskServiceImpl implements KioskService {

    private final KioskRepository kioskRepository;
    private final KioskMapper kioskMapper;

    public KioskServiceImpl(KioskRepository kioskRepository, KioskMapper kioskMapper) {
        this.kioskRepository = kioskRepository;
        this.kioskMapper = kioskMapper;
    }

    @Override
    public KioskResponseDTO createKiosk(KioskRequestDTO dto) {

        log.info("Creating new Kiosk");

        if (dto.getKioskId() == null) {
            log.warn("Validation failed : KioskId is Null");
            throw new BusinessException(
                    "KIOSK_ID_NULL",
                    "Kiosk ID cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getKioskId().isBlank()) {
            log.warn("Validation Failed : KioskId is Blank");
            throw new BusinessException(
                    "KIOSK_ID_EMPTY",
                    "Kiosk ID cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (kioskRepository.existsByKioskId(dto.getKioskId())) {
            log.warn("Validation Failed : KioskId is already Exist");
            throw new BusinessException(
                    "DUPLICATE_KIOSK_ID",
                    "Kiosk with ID '" + dto.getKioskId() + "' already exists",
                    HttpStatus.CONFLICT
            );
        }

        if (dto.getBranchId() == null) {
            log.warn("Validation Failed: BranchId is Null");
            throw new BusinessException(
                    "BRANCH_ID_NULL",
                    "Branch ID cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getBranchId().isBlank()) {
            log.warn("Validation Failed: BrandId is Blank");
            throw new BusinessException(
                    "BRANCH_ID_EMPTY",
                    "Branch ID cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getName() == null) {
            log.warn("Validation Failed: Name is Null");
            throw new BusinessException(
                    "NAME_NULL",
                    "Kiosk name cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getName().isBlank()) {
            log.warn("Validation Failed : Name is Blank");
            throw new BusinessException(
                    "NAME_EMPTY",
                    "Kiosk name cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (kioskRepository.existsByName(dto.getName())) {
            log.warn("Validation Failed : Kiosk Name already Exit");
            throw new BusinessException(
                    "DUPLICATE_NAME",
                    "Kiosk with name '" + dto.getName() + "' already exists",
                    HttpStatus.CONFLICT
            );
        }

        if (dto.getLocation() == null) {
            log.warn("Validation Failed : Location is Null");
            throw new BusinessException(
                    "LOCATION_NULL",
                    "Kiosk location cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }

        Kiosk kiosk = kioskMapper.toEntity(dto);
        log.info("Mapped KioskRequestDTO to Kiosk: {}", kiosk);

        if (kiosk.getHolidayCalendar() != null) {
            kiosk.getHolidayCalendar().forEach(holiday -> holiday.setKiosk(kiosk));
        }

        Kiosk saved = kioskRepository.save(kiosk);
        log.info("Kiosk saved with kioskId: {}", saved.getKioskId());
        return kioskMapper.toDto(saved);
    }

    @Override
    public List<KioskResponseDTO> getKiosk() {
        log.info("Fetching all Kiosk from repository");
        try {
            return kioskRepository.findAll()
                    .stream()
                    .map(kioskMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Exception while fetching kiosk: {}", e.getMessage(), e);
            throw e;
        }
    }
}
