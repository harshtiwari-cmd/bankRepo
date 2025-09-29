package com.example.card.domain.dto;

import lombok.*;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FXRateResponseDto {
    private String countryCode;

    private String countryName;

    private String currencyCode;

    private BigDecimal buyRate;

    private BigDecimal sellRate;
}
