package com.example.card.constrants.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtmResponseDto {
    private Long id;
    private String atmId;
    private String branchId;
    private String siteName;
    private String townName;
    private String country;
    private String openTime;

}