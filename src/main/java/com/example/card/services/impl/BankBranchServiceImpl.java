package com.example.card.services.impl;

import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankBranchDTO;
import com.example.card.constrants.mapper.BankBranchMapper;
import com.example.card.constrants.model.Coordinates;
import com.example.card.constrants.model.Holiday;
import com.example.card.entity.BankBranch;
import com.example.card.exceptions.BusinessException;
import com.example.card.repository.BankBranchRepository;
import com.example.card.services.BankBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankBranchServiceImpl implements BankBranchService {

    @Autowired
    private BankBranchRepository repository;

    @Override
    public List<BankBranchDTO> getAllBranches() {
        return repository.findAll()
                .stream()
                .map(BankBranchMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BankBranchDTO createBankBranch(CreateBankBranchDTO dto) {


        if (dto.getBankBranchName() == null) {
            throw new BusinessException(
                    "BRANCH_NAME_NULL",
                    "Bank branch name cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getBankBranchName().isBlank()) {
            throw new BusinessException(
                    "BRANCH_NAME_EMPTY",
                    "Bank branch name cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (repository.existsByBankBranchName(dto.getBankBranchName())) {
            throw new BusinessException(
                    "DUPLICATE_BRANCH_NAME",
                    "Branch with name '" + dto.getBankBranchName() + "' already created",
                    HttpStatus.CONFLICT
            );
        }


        if (dto.getAddress() == null) {
            throw new BusinessException(
                    "ADDRESS_NULL",
                    "Branch address cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getAddress().isBlank()) {
            throw new BusinessException(
                    "ADDRESS_EMPTY",
                    "Branch address cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (repository.existsByAddress(dto.getAddress())) {
            throw new BusinessException(
                    "DUPLICATE_ADDRESS",
                    "Branch with address '" + dto.getAddress() + "' already created",
                    HttpStatus.CONFLICT
            );
        }

        if (dto.getBranchNumber() == null) {
            throw new BusinessException(
                    "BRANCH_NUMBER_NULL",
                    "Branch number cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (dto.getBranchNumber().isBlank()) {
            throw new BusinessException(
                    "BRANCH_NUMBER_EMPTY",
                    "Branch number cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (repository.existsByBranchNumber(dto.getBranchNumber())) {
            throw new BusinessException(
                    "DUPLICATE_BRANCH_NUMBER",
                    "Branch with number '" + dto.getBranchNumber() + "' already created",
                    HttpStatus.CONFLICT
            );
        }


        if (dto.getContact() != null && repository.existsByContact(dto.getContact())) {
            throw new BusinessException(
                    "DUPLICATE_CONTACT",
                    "Branch with contact number '" + dto.getContact() + "' already created",
                    HttpStatus.CONFLICT
            );
        }


        if (dto.getCoordinates() != null) {
            Double lat = dto.getCoordinates().getLatitude();
            Double lon = dto.getCoordinates().getLongitude();

            if (lat == null || lon == null) {
                throw new BusinessException(
                        "COORDINATES_NULL",
                        "Latitude and Longitude cannot be null",
                        HttpStatus.BAD_REQUEST
                );
            }

            if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
                throw new BusinessException(
                        "INVALID_COORDINATES",
                        "Latitude must be between -90 and 90, Longitude between -180 and 180",
                        HttpStatus.UNPROCESSABLE_ENTITY
                );}
            if (repository.existsByCoordinatesLatitudeAndCoordinatesLongitude(lat, lon)) {
                throw new BusinessException(
                        "DUPLICATE_COORDINATES",
                        "Branch with coordinates (lat=" + lat + ", lon=" + lon + ") already created",
                        HttpStatus.CONFLICT
                );
            }
    }

        BankBranch entity = new BankBranch();
        entity.setBankName(dto.getBankName());
        entity.setBankBranchName(dto.getBankBranchName());
        entity.setBranchNumber(dto.getBranchNumber());
        entity.setDescription(dto.getDescription());
        entity.setBankBranchType(dto.getBankBranchType());
        entity.setCountryName(dto.getCountryName());
        entity.setOpenTime(dto.getOpenTime());
        entity.setCloseTime(dto.getCloseTime());
        entity.setAddress(dto.getAddress());
        entity.setLocation(dto.getLocation());
        entity.setContact(dto.getContact());

        if (dto.getCoordinates() != null) {
            entity.setCoordinates(
                    new Coordinates(
                            dto.getCoordinates().getLatitude(),
                            dto.getCoordinates().getLongitude()
                    )
            );
        }

        if (dto.getHolidayCalendar() != null && !dto.getHolidayCalendar().isEmpty()) {
            entity.setHolidayCalendar(
                    dto.getHolidayCalendar()
                            .stream()
                            .map(h -> new Holiday(h.getDate(), h.getName(), h.getType()))
                            .collect(Collectors.toList())
            );
        } else {
            entity.setHolidayCalendar(null);
        }

        entity.setWeeklyHolidayList(dto.getWeeklyHolidays());

        BankBranch saved = repository.save(entity);
        return BankBranchMapper.toDTO(saved);
    }
}
