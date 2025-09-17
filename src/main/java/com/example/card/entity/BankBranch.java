package com.example.card.entity;

import com.example.card.constrants.model.Coordinates;
import com.example.card.constrants.model.Holiday;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name = "bank_branches")
public class BankBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankName;
    private String bankBranchName;
    private String branchNumber;
    private String description;
    private String address;
    private String contact;
    private String location;
    private String bankBranchType;
    private String countryName;

    @Embedded
    private Coordinates coordinates;

    private String openTime;
    private String closeTime;

    private String holidayDates;
    private String holidayNames;
    private String holidayTypes;

    private String weeklyHolidays;


    @Transient
    public List<Holiday> getHolidayCalendar() {
        if (holidayDates == null || holidayDates.isEmpty()) return Collections.emptyList();

        String[] dates = holidayDates.split(",");
        String[] names = holidayNames != null ? holidayNames.split(",") : new String[dates.length];
        String[] types = holidayTypes != null ? holidayTypes.split(",") : new String[dates.length];

        return IntStream.range(0, dates.length)
                .mapToObj(i -> new Holiday(
                        dates[i].trim(),
                        i < names.length ? names[i].trim() : "",
                        i < types.length ? types[i].trim() : ""))
                .collect(Collectors.toList());
    }

    public void setHolidayCalendar(List<Holiday> holidays) {
        if (holidays == null || holidays.isEmpty()) {
            holidayDates = "";
            holidayNames = "";
            holidayTypes = "";
            return;
        }
        holidayDates = holidays.stream().map(Holiday::getDate).collect(Collectors.joining(","));
        holidayNames = holidays.stream().map(Holiday::getName).collect(Collectors.joining(","));
        holidayTypes = holidays.stream().map(Holiday::getType).collect(Collectors.joining(","));
    }


    @Transient
    public List<String> getWeeklyHolidayList() {
        if (weeklyHolidays == null || weeklyHolidays.isEmpty()) return Collections.emptyList();
        return Arrays.stream(weeklyHolidays.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public void setWeeklyHolidayList(List<String> holidays) {
        if (holidays == null || holidays.isEmpty()) {
            weeklyHolidays = "";
        } else {
            weeklyHolidays = String.join(",", holidays);
        }
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBankBranchName() { return bankBranchName; }
    public void setBankBranchName(String bankBranchName) { this.bankBranchName = bankBranchName; }

    public String getBranchNumber() { return branchNumber; }
    public void setBranchNumber(String branchNumber) { this.branchNumber = branchNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBankBranchType() { return bankBranchType; }
    public void setBankBranchType(String bankBranchType) { this.bankBranchType = bankBranchType; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public Coordinates getCoordinates() { return coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

    public String getOpenTime() { return openTime; }
    public void setOpenTime(String openTime) { this.openTime = openTime; }

    public String getCloseTime() { return closeTime; }
    public void setCloseTime(String closeTime) { this.closeTime = closeTime; }

    public String getHolidayDates() { return holidayDates; }
    public void setHolidayDates(String holidayDates) { this.holidayDates = holidayDates; }

    public String getHolidayNames() { return holidayNames; }
    public void setHolidayNames(String holidayNames) { this.holidayNames = holidayNames; }

    public String getHolidayTypes() { return holidayTypes; }
    public void setHolidayTypes(String holidayTypes) { this.holidayTypes = holidayTypes; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeeklyHolidays() { return weeklyHolidays; }
    public void setWeeklyHolidays(String weeklyHolidays) { this.weeklyHolidays = weeklyHolidays; }
}