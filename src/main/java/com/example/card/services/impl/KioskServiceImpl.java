package com.example.card.services.impl;

import com.example.card.constrants.mapper.KioskMapper;
import com.example.card.constrants.model.Kiosk;
import com.example.card.dto.KioskRequestDTO;
import com.example.card.dto.KioskResponseDTO;
import com.example.card.exceptions.BusinessException;
import com.example.card.repository.KioskRepository;
import com.example.card.services.KioskService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (dto.getKioskId() == null) {
            throw new BusinessException(
                    "KIOSK_ID_NULL",
                    "Kiosk ID cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getKioskId().isBlank()) {
            throw new BusinessException(
                    "KIOSK_ID_EMPTY",
                    "Kiosk ID cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (kioskRepository.existsByKioskId(dto.getKioskId())) {
            throw new BusinessException(
                    "DUPLICATE_KIOSK_ID",
                    "Kiosk with ID '" + dto.getKioskId() + "' already exists",
                    HttpStatus.CONFLICT
            );
        }

        if (dto.getBranchId() == null) {
            throw new BusinessException(
                    "BRANCH_ID_NULL",
                    "Branch ID cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getBranchId().isBlank()) {
            throw new BusinessException(
                    "BRANCH_ID_EMPTY",
                    "Branch ID cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getName() == null) {
            throw new BusinessException(
                    "NAME_NULL",
                    "Kiosk name cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getName().isBlank()) {
            throw new BusinessException(
                    "NAME_EMPTY",
                    "Kiosk name cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (kioskRepository.existsByName(dto.getName())) {
            throw new BusinessException(
                    "DUPLICATE_NAME",
                    "Kiosk with name '" + dto.getName() + "' already exists",
                    HttpStatus.CONFLICT
            );
        }

        if (dto.getLocation() == null) {
            throw new BusinessException(
                    "LOCATION_NULL",
                    "Kiosk location cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getLocation() == null || dto.getLocation().getGeoLocation() == null ||
                dto.getLocation().getGeoLocation().getLatitude() == null ||
                dto.getLocation().getGeoLocation().getLongitude() == null) {
            throw new BusinessException(
                    "COORDINATES_NULL",
                    "Latitude and Longitude cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getLocation().getGeoLocation().getLatitude() < -90 || dto.getLocation().getGeoLocation().getLatitude() > 90 ||
                dto.getLocation().getGeoLocation().getLongitude() < -180 || dto.getLocation().getGeoLocation().getLongitude() > 180) {
            throw new BusinessException(
                    "INVALID_COORDINATES",
                    "Latitude must be between -90 and 90, Longitude between -180 and 180",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }


        if (dto.getLocation() != null &&
                dto.getLocation().getGeoLocation() != null &&
                dto.getLocation().getGeoLocation().getLatitude() != null &&
                dto.getLocation().getGeoLocation().getLongitude() != null &&
                kioskRepository.existsByLocationGeoLocationLatitudeAndLocationGeoLocationLongitude(
                        dto.getLocation().getGeoLocation().getLatitude(),
                        dto.getLocation().getGeoLocation().getLongitude())) {

            throw new BusinessException(
                    "DUPLICATE_COORDINATES",
                    "Kiosk with coordinates (lat=" + dto.getLocation().getGeoLocation().getLatitude()
                            + ", lon=" + dto.getLocation().getGeoLocation().getLongitude() + ") already exists",
                    HttpStatus.CONFLICT
            );
        }

        Kiosk kiosk = kioskMapper.toEntity(dto);

        if (kiosk.getHolidayCalendar() != null) {
            kiosk.getHolidayCalendar().forEach(holiday -> holiday.setKiosk(kiosk));
        }

        Kiosk saved = kioskRepository.save(kiosk);
        return kioskMapper.toDto(saved);
    }
}
