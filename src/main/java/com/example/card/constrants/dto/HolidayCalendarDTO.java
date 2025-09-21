package com.example.card.constrants.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class HolidayCalendarDTO {

    private Long id;

    @NotNull(message = "Date should not be null")
    private String date;

    @NotBlank(message = "name should not be blank")
    private String name;
}