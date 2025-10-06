package com.example.card.domain.dto;

import lombok.*;

import java.util.Date;

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

    private String locatorType; // renamed from typeLocation for consistency

    private String workingHours;

    private String workingHoursInArb;

    // ✅ Additional Fields
    private String searchString;

    private String facility;

    private String address;

    private String atmType;

    private String currencySupported;

    private String isActive; // Y or N

    private Date installationDate;

    private String maintenanceVendor;
}
