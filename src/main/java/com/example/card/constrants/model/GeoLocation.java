package com.example.card.constrants.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class GeoLocation {

    private Float latitude;
    private Float longitude;

}

