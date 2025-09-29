package com.example.card.domain.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Builder
public class AtmRequestDto {

    private String arabicName;

    @NotNull(message = "Cash Deposit flag is required")
    private Boolean cashDeposit;

    @NotNull(message = "Cash Out flag is required")
    private Boolean cashOut;

    @NotNull(message = "Cheque Deposit flag is required")
    private Boolean chequeDeposit;

    private String city;

    private String cityInArabic;

    @NotBlank(message = "Code is required")
    private String code;

    private String contactDetails;

    private String country;

    @NotNull(message = "Disable People flag is required")
    private Boolean disablePeople;

    private String fullAddress;

    private String fullAddressArb;

    private String latitude;

    private String longitude;

    @NotNull(message = "Online Location flag is required")
    private Boolean onlineLocation;

    private String timing;

    private String typeLocation;

    private String workingHours;

    private String workingHoursInArb;
}