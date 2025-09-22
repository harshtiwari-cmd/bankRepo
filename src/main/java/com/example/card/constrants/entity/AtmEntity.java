package com.example.card.constrants.entity;

import com.example.card.constrants.model.Coordinates;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="atm_branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String atmId;
    private String branchId;
    private String siteName;
    private String streetName;
    private String townName;
    private String country;
    private String postCode;

    @Embedded
    private Coordinates coordinates;


    private String supportedLanguages;

    @ElementCollection
    @CollectionTable(name = "atm_services",joinColumns = @JoinColumn (name = "atm_id"))
    @Column(name = "service")
    private List<String> atmServices;


    private String supportedCurrencies;

    private Integer minimumPossibleAmount;
    private String openTime;




}