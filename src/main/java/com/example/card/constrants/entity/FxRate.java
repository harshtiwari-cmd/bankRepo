package com.example.card.constrants.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "fx_rates")
@NoArgsConstructor
@AllArgsConstructor
public class FxRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "buy_rate", precision = 15, scale = 6, nullable = false)
    private BigDecimal buyRate;

    @Column(name = "sell_rate", precision = 15, scale = 6, nullable = false)
    private BigDecimal sellRate;


}
