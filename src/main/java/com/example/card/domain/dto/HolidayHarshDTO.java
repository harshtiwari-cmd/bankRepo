package com.example.card.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayHarshDTO {

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in YYYY-MM-DD format")
    private String date;

    @NotBlank(message = "Holiday name must not be blank")
    private String name;

    @NotBlank(message = "Holiday type must not be blank")
    private String type;

}
