package com.example.card.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesDTO {
    private double latitude;
    private double longitude;
}
