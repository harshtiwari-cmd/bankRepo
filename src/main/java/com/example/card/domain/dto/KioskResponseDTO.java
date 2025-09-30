package com.example.card.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class KioskResponseDTO {

    private String kioskId;
    private String branchId;
    private String name;
    private String description;
    private LocationDTO location;
    private CoordinatesDTO coordinates;
    private List<String> kioskServices;
    private String openTime;
    private String closeTime;
    private List<HolidayCalendarDTO> holidayCalendar;
    private List<String> weeklyHolidays;

}
