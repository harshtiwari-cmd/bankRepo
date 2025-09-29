package com.example.card.constrants.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ATM_LOCATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ARABIC_NAME", length = 255)
    private String arabicName;

    @Column(name = "CASH_DEPOSIT", nullable = false)
    private Boolean cashDeposit;

    @Column(name = "CASH_OUT", nullable = false)
    private Boolean cashOut;

    @Column(name = "CHEQUE_DEPOSIT", nullable = false)
    private Boolean chequeDeposit;

    @Column(name = "CITY", length = 255)
    private String city;

    @Column(name = "CITY_IN_ARABIC", length = 255)
    private String cityInArabic;

    @Column(name = "CODE", length = 255, nullable = false)
    private String code;

    @Column(name = "CONTACT_DETAILS", length = 255)
    private String contactDetails;

    @Column(name = "COUNTRY", length = 255)
    private String country;

    @Column(name = "DISABLE_PEOPLE", nullable = false)
    private Boolean disablePeople;

    @Column(name = "FULL_ADDRESS", length = 255)
    private String fullAddress;

    @Column(name = "FULL_ADDRESS_ARB", length = 255)
    private String fullAddressArb;

    @Column(name = "LATITUDE", length = 255)
    private String latitude;

    @Column(name = "LONGITUDE", length = 255)
    private String longitude;

    @Column(name = "ONLINE_LOCATION", nullable = false)
    private Boolean onlineLocation;

    @Column(name = "TIMING", length = 255)
    private String timing;

    @Column(name = "TYPE_LOCATION", length = 255)
    private String typeLocation;

    @Column(name = "WORKING_HOURS", length = 255)
    private String workingHours;

    @Column(name = "WORKING_HOURS_IN_ARB", length = 255)
    private String workingHoursInArb;
}