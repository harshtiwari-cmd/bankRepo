package com.example.card.constrants.dto;

import lombok.Data;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

@Data
public class ProfitRateRequestDTO {

    private Long id;

    @NotBlank(message = "Product name must not be blank")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String productName;

    @NotNull(message = "Rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be greater than 0")
    @Digits(integer = 3, fraction = 2, message = "Rate must be a valid percentage (e.g., 5.25)")
    private Double rate;

    @NotNull(message = "Effective from date is required")
    @FutureOrPresent(message = "Effective from date must be today or in the future")
    private LocalDate effectiveFrom;

    @Future(message = "Effective to date must be in the future")
    private LocalDate effectiveTo;

    @NotNull(message = "Rate type (fixed or variable) must be specified")
    private Boolean isFixed;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
}