package com.example.card.services.impl;


import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;
import com.example.card.constrants.mapper.BankBranchHarshMapper;
import com.example.card.constrants.model.CoordinatesHarsh;
import com.example.card.constrants.model.HolidayHarsh;
import com.example.card.entity.BankBranchHarsh;
import com.example.card.repository.BankBranchRepositoryHarsh;
import com.example.card.services.BankBranchServiceHarsh;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankBranchServiceHarshImplHarsh implements BankBranchServiceHarsh {

    @Autowired
    private BankBranchRepositoryHarsh repository;

    @Override
    public List<BankBranchHarshDTO> getAllBranches() {
        return repository.findAll()
                .stream()
                .map(BankBranchHarshMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public BankBranchHarshDTO createBankBranch(CreateBankHarshBranchDTO dto) {
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
        return BankBranchHarshMapper.toDTO(saved);
    }
}