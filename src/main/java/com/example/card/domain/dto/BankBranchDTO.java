package com.example.card.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BankBranchDTO {
    private Long id;
    private String bankName;
    private String bankBranchName;
    private String branchNumber;
    private String description;
    private String bankBranchType;
    private String countryName;
    private CoordinatesHarshDTO coordinates;
    private String openTime;
    private String address;
    private String location;
    private String contact;
    private String closeTime;
    private List<HolidayHarshDTO> holidayCalendar;
    private List<String> weeklyHolidays;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;
}
