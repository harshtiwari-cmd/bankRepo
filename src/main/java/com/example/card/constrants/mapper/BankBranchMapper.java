package com.example.card.constrants.mapper;

import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CoordinatesDTO;
import com.example.card.constrants.dto.HolidayDTO;
import com.example.card.entity.BankBranch;

import java.util.stream.Collectors;

public class BankBranchMapper {
    public static BankBranchDTO toDTO(BankBranch entity) {
        if (entity == null) return null;

        BankBranchDTO dto = new BankBranchDTO();
        dto.setId(entity.getId());
        dto.setBankName(entity.getBankName());
        dto.setBankBranchName(entity.getBankBranchName());
        dto.setBranchNumber(entity.getBranchNumber());
        dto.setDescription(entity.getDescription());
        dto.setBankBranchType(entity.getBankBranchType());
        dto.setCountryName(entity.getCountryName());
        dto.setLocation(entity.getLocation());
        dto.setAddress(entity.getAddress());
        dto.setContact(entity.getContact());
        dto.setOpenTime(entity.getOpenTime());
        dto.setCloseTime(entity.getCloseTime());

        if (entity.getCoordinates() != null) {
            CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
            coordinatesDTO.setLatitude(entity.getCoordinates().getLatitude());
            coordinatesDTO.setLongitude(entity.getCoordinates().getLongitude());
            dto.setCoordinates(coordinatesDTO);
        }

        dto.setHolidayCalendar(entity.getHolidayCalendar()
                .stream()
                .map(h -> {
                    HolidayDTO hdto = new HolidayDTO();
                    hdto.setDate(h.getDate());
                    hdto.setName(h.getName());
                    hdto.setType(h.getType());
                    return hdto;
                }).collect(Collectors.toList()));

        dto.setWeeklyHolidays(entity.getWeeklyHolidayList());

        return dto;
    }
}
