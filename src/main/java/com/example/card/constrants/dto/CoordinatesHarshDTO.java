package com.example.card.constrants.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinatesHarshDTO {
    @NotNull(message = "Latitude should not be null")
    private double latitude;
    @NotNull(message = "Longitude should not be null")
    private double longitude;
}
