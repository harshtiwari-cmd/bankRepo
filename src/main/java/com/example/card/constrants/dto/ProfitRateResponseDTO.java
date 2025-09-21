package com.example.card.constrants.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfitRateResponseDTO {

    private Long id;

    private String productName;

    private Double rate;

    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    private Boolean isFixed;

    private String description;
}
