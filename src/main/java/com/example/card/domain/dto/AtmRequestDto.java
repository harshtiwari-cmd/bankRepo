package com.example.card.domain.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Size(max = 100)
    private String locatorType; // renamed from typeLocation

    private String workingHours;

    private String workingHoursInArb;

    private String searchString;

    private String facility;

    private String address;

    private String atmType;

    @Size(max = 50)
    private String currencySupported;

    @Pattern(regexp = "Y|N", message = "isActive must be 'Y' or 'N'")
    private String isActive = "Y"; // default value

    private Date installationDate;

    private String maintenanceVendor;
}
