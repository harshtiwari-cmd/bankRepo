package com.example.card.constrants.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "RBX_T_LOCATOR")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATOR_ID")
    private Long id;

    @Column(name = "LOCATOR_TYPE", length = 100)
    private String locatorType;

    @Column(name = "SEARCH_STRING", length = 255)
    private String searchString;

    @Column(name = "LATITUDE", length = 100)
    private String latitude;

    @Column(name = "LONGITUDE", length = 100)
    private String longitude;

    @Column(name = "FACILITY", length = 255)
    private String facility;

    @Column(name = "ADDRESS", length = 500)
    private String address;

    @Column(name = "ARABIC_NAME", length = 255)
    private String arabicName;

    @NotNull
    @Column(name = "CASH_DEPOSIT", nullable = false)
    private Boolean cashDeposit = false;

    @NotNull
    @Column(name = "CASH_OUT", nullable = false)
    private Boolean cashOut = false;

    @NotNull
    @Column(name = "CHEQUE_DEPOSIT", nullable = false)
    private Boolean chequeDeposit = false;

    @Column(name = "CITY", length = 255)
    private String city;

    @Column(name = "CITY_IN_ARABIC", length = 255)
    private String cityInArabic;

    @NotBlank
    @Column(name = "CODE", length = 255, nullable = false)
    private String code;

    @Column(name = "CONTACT_DETAILS", length = 255)
    private String contactDetails;

    @Column(name = "COUNTRY", length = 255)
    private String country;

    @NotNull
    @Column(name = "DISABLE_PEOPLE", nullable = false)
    private Boolean disablePeople = false;

    @Column(name = "FULL_ADDRESS", length = 255)
    private String fullAddress;

    @Column(name = "FULL_ADDRESS_ARB", length = 255)
    private String fullAddressArb;

    @NotNull
    @Column(name = "ONLINE_LOCATION", nullable = false)
    private Boolean onlineLocation = false;

    @Column(name = "TIMING", length = 255)
    private String timing;

    @Column(name = "WORKING_HOURS", length = 255)
    private String workingHours;

    @Column(name = "WORKING_HOURS_IN_ARB", length = 255)
    private String workingHoursInArb;

    @Column(name = "ATM_TYPE", length = 100)
    private String atmType;

    @Column(name = "CURRENCY_SUPPORTED", length = 50)
    private String currencySupported;

    @Column(name = "IS_ACTIVE", length = 1, nullable = false)
    @Pattern(regexp = "Y|N", message = "isActive must be 'Y' or 'N'")
    private String isActive = "Y";


    @Column(name = "INSTALLATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date installationDate;

    @Column(name = "MAINTENANCE_VENDOR", length = 255)
    private String maintenanceVendor;
}
