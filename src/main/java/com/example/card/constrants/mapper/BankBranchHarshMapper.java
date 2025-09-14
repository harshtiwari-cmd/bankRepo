package com.example.card.constrants.mapper;

import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.constrants.dto.CoordinatesHarshDTO;
import com.example.card.constrants.dto.HolidayHarshDTO;
import com.example.card.entity.BankBranchHarsh;

import java.util.stream.Collectors;

public class BankBranchHarshMapper {
    public static BankBranchHarshDTO toDTO(BankBranchHarsh entity) {
        if (entity == null) return null;

        BankBranchHarshDTO dto = new BankBranchHarshDTO();
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
            CoordinatesHarshDTO coordinatesHarshDTO = new CoordinatesHarshDTO();
            coordinatesHarshDTO.setLatitude(entity.getCoordinates().getLatitude());
            coordinatesHarshDTO.setLongitude(entity.getCoordinates().getLongitude());
            dto.setCoordinates(coordinatesHarshDTO);
        }

        dto.setHolidayCalendar(entity.getHolidayCalendar()
                .stream()
                .map(h -> {
                    HolidayHarshDTO hdto = new HolidayHarshDTO();
                    hdto.setDate(h.getDate());
                    hdto.setName(h.getName());
                    hdto.setType(h.getType());
                    return hdto;
                }).collect(Collectors.toList()));

        dto.setWeeklyHolidays(entity.getWeeklyHolidayList());

        return dto;
    }
}
