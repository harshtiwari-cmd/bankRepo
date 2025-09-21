package com.example.card.constrants.dto;

import com.example.card.constrants.model.Coordinates;
import com.example.card.constrants.model.GeoLocation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtmRequestDto {

    @NotBlank(message = "ATM ID Is required")
    private String atmId;
    @NotBlank(message = "Branch ID is required")
    private String branchId;
    @NotBlank(message = "Site name is required")
    private String siteName;
    @NotBlank(message = "Street name is required")
    private String streetName;
    @NotBlank(message = "Town name is required")
    private String townName;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "Post Code is required")
    private String postCode;

    @Valid
    @NotNull(message = "GeoLocation is required")
    private Coordinates coordinates;


    @NotEmpty(message = "Supported Language must not be empty")
    private List<@NotBlank(message = "Language must not be blank") String> supportedLanguages;

    @NotEmpty(message = "ATM services must not be empty")
    private List<@NotBlank(message = "Service must not be blank") String> atmServices;

    @NotEmpty(message = "Supported currencies must not be empty")
    private List<@NotBlank(message = "Currency must not be blank") String> supportedCurrencies;

    @NotNull(message = "Minimum possible amount is required")
    @Min(value = 1, message = "Minimum amount must be at least 1")
    private Integer minimumPossibleAmount;

    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Open time must be in HH:mm format")
    private String openTime;

}