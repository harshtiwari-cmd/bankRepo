package com.example.card.services.impl;


import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import com.example.card.constrants.mapper.BankBranchMapper;
import com.example.card.constrants.model.CoordinatesHarsh;
import com.example.card.constrants.model.HolidayHarsh;
import com.example.card.constrants.entity.BankBranchHarsh;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.repository.BankBranchRepository;
import com.example.card.services.BankBranchService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BankBranchServiceImpl implements BankBranchService {

    @Autowired
    private BankBranchRepository repository;

    @Override
    public List<BankBranchDTO> getAllBranches() {
        log.info("Fetching all bank branches");

        List<BankBranchDTO> branches = repository.findAll()
                .stream()
                .map(BankBranchMapper::toDTO)
                .collect(Collectors.toList());

        log.info("Fetched {} bank branches", branches.size());
        return branches;
    }
    @Override
    public BankBranchDTO createBankBranch(CreateBankHarshBranchDTO dto) {
        log.info("Received request to create bank branch: {}", dto.getBankBranchName());
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

            log.debug("Coordinates set for branch: {}", dto.getCoordinates());
        }

        if (dto.getHolidayCalendar() != null) {
            entity.setHolidayCalendar(dto.getHolidayCalendar()
                    .stream()
                    .map(h -> new HolidayHarsh(h.getDate(), h.getName(), h.getType()))
                    .collect(Collectors.toList()));
            log.debug("Holiday calendar set with {} holidays", dto.getHolidayCalendar().size());
        } else {
            entity.setHolidayCalendar(null);
        }

        entity.setWeeklyHolidayList(dto.getWeeklyHolidays());

        BankBranchHarsh saved = repository.save(entity);
        log.info("Bank branch created with ID: {}", saved.getId());
        return BankBranchMapper.toDTO(saved);
    }
    @Override
    public boolean isBranchOpen(Long branchId, LocalDateTime dateTime) {
        log.info("Checking if branch with ID {} is open at {}", branchId, dateTime);
        BankBranchDTO branch = getBranchById(branchId);

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        if (branch.getWeeklyHolidays().stream()
                .anyMatch(holiday -> holiday.equalsIgnoreCase(dayOfWeek.name()))) {
            log.info("Branch {} is closed on weekly holiday: {}", branchId, dayOfWeek);
            return false;
        }

        boolean isSpecialHoliday = branch.getHolidayCalendar().stream()
                .anyMatch(h -> h.getDate().equals(dateTime.toLocalDate().toString()));
        if (isSpecialHoliday) {
            log.info("Branch {} is closed on special holiday: {}", branchId, dateTime.toLocalDate());
            return false;
        }

        LocalTime open = LocalTime.parse(branch.getOpenTime());
        LocalTime close = LocalTime.parse(branch.getCloseTime());
        LocalTime time = dateTime.toLocalTime();

        if (time.isBefore(open) || time.isAfter(close)) {
            log.info("Branch {} is closed at {}, outside working hours ({} - {})", branchId, time, open, close);
            return false;
        }
        log.info("Branch {} is open at {}", branchId, time);
        return true;
    }
    public BankBranchDTO getBranchById(Long branchId) {
        log.debug("Fetching branch by ID: {}", branchId);
        BankBranchHarsh entity = repository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch with ID " + branchId + " not found"));
        log.debug("Branch found: {}", entity.getBankBranchName());
        return BankBranchMapper.toDTO(entity);
    }

    public List<BankBranchDTO> getAllBranchesWithStatus() {
        log.info("Fetching all branches with open/closed status");

        List<BankBranchDTO> branches = getAllBranches();

        LocalDateTime now = LocalDateTime.now();

        return branches.stream()
                .filter(b -> b.getOpenTime() != null && b.getCloseTime() != null)
                .peek(b -> {
                    boolean open = isBranchOpen(b.getId(), now);
                    b.setStatus(open ? "Open" : "Closed");
                })
                .collect(Collectors.toList());
    }
}