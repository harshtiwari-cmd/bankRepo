package com.example.card.constrants.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeoLocationDTO {


    @NotNull(message = "Latitude should not be null")
    private Float latitude;
    @NotNull(message = "Longitude should not be null")
    private Float longitude;
}

