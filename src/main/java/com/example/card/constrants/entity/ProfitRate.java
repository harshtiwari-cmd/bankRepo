package com.example.card.constrants.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "profit_rates")
public class ProfitRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Double rate;

    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    private Boolean isFixed;

    private String description;
}
