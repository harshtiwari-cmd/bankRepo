package com.example.card.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LocateUsDTO {

    private String locatorType;
    private String searchString;

    // latitude/longitude represented via coordinates
    private CoordinatesDTO coordinates;

    private String facility;
    private String address;
    private String arabicName;

    private Integer cashDeposit; // 0/1
    private Integer cashOut; // 0/1
    private Integer chequeDeposit; // 0/1

    private String city;
    private String cityInArabic;
    private String code;
    private String contactDetails;
    private String country;
    private Integer disablePeople; // 0/1
    private String fullAddress;
    private String fullAddressArb;
    private Integer onlineLocation; // 0/1
    private String timing;
    private String typeLocation;
    private String workingHours;
    private String workingHoursInArb;
    private String status; // e.g., OPEN, CLOSED, UNKNOWN

    private Date dateCreate;
    private String userCreate;
    private Date dateModif;
    private String userModif;

    private String maintenanceVendor;
    private String atmType;
    private String currencySupported;
    private String isActive; // 'Y' or 'N'
    private Date installationDate;
}


