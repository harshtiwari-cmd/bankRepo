package com.example.card.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtmResponseDto {
    private Long id;

    private String arabicName;

    private Boolean cashDeposit;

    private Boolean cashOut;

    private Boolean chequeDeposit;

    private String city;

    private String cityInArabic;

    private String code;

    private String contactDetails;

    private String country;

    private Boolean disablePeople;

    private String fullAddress;

    private String fullAddressArb;

    private String latitude;

    private String longitude;

    private Boolean onlineLocation;

    private String timing;

    private String typeLocation;

    private String workingHours;

    private String workingHoursInArb;
}