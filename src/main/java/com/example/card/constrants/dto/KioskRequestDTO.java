package com.example.card.constrants.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class KioskRequestDTO {

    private String kioskId;

    @NotBlank(message = "branch id should not be blank")
    private String branchId;

    @NotBlank(message = "name should not be blank")
    private String name;

    private String description;

    @Valid
    private LocationDTO location;

    @NotEmpty
    private List<String> kioskServices;

    private String openTime;

    private String closeTime;


    @Valid
    private List<HolidayCalendarDTO> holidayCalendar;

    private List<String> weeklyHolidays;

}
