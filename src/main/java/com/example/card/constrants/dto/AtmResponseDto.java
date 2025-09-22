package com.example.card.constrants.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AtmResponseDto {
    private Long id;
    private String atmId;
    private String branchId;
    private String siteName;
    private String townName;
    private CoordinatesDTO coordinates;
    private String country;
    private String openTime;

}