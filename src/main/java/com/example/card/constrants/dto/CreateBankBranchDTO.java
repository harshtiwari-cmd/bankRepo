package com.example.card.constrants.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateBankBranchDTO {
    private String bankName;
    private String bankBranchName;
    private String branchNumber;
    private String description;
    private String bankBranchType;
    private String countryName;
    private CoordinatesDTO coordinates;
    private String openTime;
    private String address;
    private String location;
    private String contact;
    private String closeTime;
    private List<HolidayDTO> holidayCalendar;
    private List<String> weeklyHolidays;
}
