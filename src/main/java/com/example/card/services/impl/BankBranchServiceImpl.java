package com.example.card.services.impl;


import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import com.example.card.constrants.mapper.BankBranchMapper;
import com.example.card.constrants.model.CoordinatesHarsh;
import com.example.card.constrants.model.HolidayHarsh;
import com.example.card.entity.BankBranchHarsh;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.repository.BankBranchRepositoryHarsh;
import com.example.card.services.BankBranchService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankBranchServiceImpl implements BankBranchService {

    @Autowired
    private BankBranchRepositoryHarsh repository;

    @Override
    public List<BankBranchDTO> getAllBranches() {
        return repository.findAll()
                .stream()
                .map(BankBranchMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public BankBranchDTO createBankBranch(CreateBankHarshBranchDTO dto) {
        BankBranchHarsh entity = new BankBranchHarsh();

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
            entity.setCoordinates(new CoordinatesHarsh(dto.getCoordinates().getLatitude(), dto.getCoordinates().getLongitude()));
        }

        if (dto.getHolidayCalendar() != null) {
            entity.setHolidayCalendar(dto.getHolidayCalendar()
                    .stream()
                    .map(h -> new HolidayHarsh(h.getDate(), h.getName(), h.getType()))
                    .collect(Collectors.toList()));
        } else {
            entity.setHolidayCalendar(null);
        }

        entity.setWeeklyHolidayList(dto.getWeeklyHolidays());

        BankBranchHarsh saved = repository.save(entity);
        return BankBranchMapper.toDTO(saved);
    }
    @Override
    public boolean isBranchOpen(Long branchId, LocalDateTime dateTime) {
        BankBranchDTO branch = getBranchById(branchId);

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        if (branch.getWeeklyHolidays().stream()
                .anyMatch(holiday -> holiday.equalsIgnoreCase(dayOfWeek.name()))) {
            return false;
        }

        boolean isSpecialHoliday = branch.getHolidayCalendar().stream()
                .anyMatch(h -> h.getDate().equals(dateTime.toLocalDate().toString()));
        if (isSpecialHoliday) {
            return false;
        }

        LocalTime open = LocalTime.parse(branch.getOpenTime());
        LocalTime close = LocalTime.parse(branch.getCloseTime());
        LocalTime time = dateTime.toLocalTime();

        if (time.isBefore(open) || time.isAfter(close)) {
            return false;
        }

        return true;
    }
    public BankBranchDTO getBranchById(Long branchId) {
        BankBranchHarsh entity = repository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch with ID " + branchId + " not found"));
        return BankBranchMapper.toDTO(entity);
    }
}