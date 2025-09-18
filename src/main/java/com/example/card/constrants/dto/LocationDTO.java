package com.example.card.constrants.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class LocationDTO {

    @NotBlank(message = "Street Name must not be blank")
    private String streetName;
    @NotBlank(message = "Town Name must not be blank")
    private String townName;
    @NotBlank(message = "Country Name must not be blank")
    private String country;
    @NotBlank(message = "PostCode must not be blank")
    private String postCode;

    @Valid
    private GeoLocationDTO geoLocation;
}