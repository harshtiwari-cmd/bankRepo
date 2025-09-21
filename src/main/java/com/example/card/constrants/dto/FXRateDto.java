package com.example.card.constrants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FXRateDto {


    private String countryCode;

    private String countryName;

    private String currencyCode;

    private BigDecimal buyRate;

    private BigDecimal sellRate;

}
