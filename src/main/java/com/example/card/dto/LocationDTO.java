package com.example.card.dto;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class LocationDTO {

    private String streetName;
    private String townName;
    private String country;
    private String postCode;

    @Valid
    private GeoLocationDTO geoLocation;


}