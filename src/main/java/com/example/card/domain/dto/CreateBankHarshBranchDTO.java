package com.example.card.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateBankHarshBranchDTO {

    @NotBlank(message = "Bank Name is required")
    private String bankName;
    @NotBlank(message = "Bank Branch Name is required")
    private String bankBranchName;
    @NotBlank(message = "Branch Number is required")
    private String branchNumber;
    @Size(max = 250, message = "Description should not exceed 250 characters")
    private String description;
    @NotBlank(message = "Bank Branch Type is required")
    private String bankBranchType;
    @NotBlank(message = "Country is required")
    private String countryName;
    @Valid
    private CoordinatesHarshDTO coordinates;

    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Open time must be in HH:mm format")
    private String openTime;

    @NotBlank(message = "Address must not be blank")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @NotBlank(message = "Location must not be blank")
    private String location;

    @NotBlank(message = "Contact must not be blank")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Contact must be a valid phone number")
    private String contact;

    @NotBlank(message = "Close time must not be blank")
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Close time must be in HH:mm format")
    private String closeTime;

    @NotEmpty(message = "Holiday calendar must not be empty")
    @Valid
    private List<HolidayHarshDTO> holidayCalendar;

    @NotEmpty(message = "Weekly holidays must not be empty")
    private List<@NotBlank(message = "weeklyHolidays must not be blank") String> weeklyHolidays;

}
