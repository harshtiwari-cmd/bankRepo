package com.example.card.entity;

import com.example.card.constrants.model.CoordinatesHarsh;
import com.example.card.constrants.model.HolidayHarsh;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name = "bank_branches")
public class BankBranchHarsh {

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
    private CoordinatesHarsh coordinatesHarsh;

    private String openTime;
    private String closeTime;

    private String holidayDates;
    private String holidayNames;
    private String holidayTypes;

    private String weeklyHolidays;


    @Transient
    public List<HolidayHarsh> getHolidayCalendar() {
        if (holidayDates == null || holidayDates.isEmpty()) return Collections.emptyList();

        String[] dates = holidayDates.split(",");
        String[] names = holidayNames != null ? holidayNames.split(",") : new String[dates.length];
        String[] types = holidayTypes != null ? holidayTypes.split(",") : new String[dates.length];

        return IntStream.range(0, dates.length)
                .mapToObj(i -> new HolidayHarsh(
                        dates[i].trim(),
                        i < names.length ? names[i].trim() : "",
                        i < types.length ? types[i].trim() : ""))
                .collect(Collectors.toList());
    }

    public void setHolidayCalendar(List<HolidayHarsh> holidayHarshes) {
        if (holidayHarshes == null || holidayHarshes.isEmpty()) {
            holidayDates = "";
            holidayNames = "";
            holidayTypes = "";
            return;
        }
        holidayDates = holidayHarshes.stream().map(HolidayHarsh::getDate).collect(Collectors.joining(","));
        holidayNames = holidayHarshes.stream().map(HolidayHarsh::getName).collect(Collectors.joining(","));
        holidayTypes = holidayHarshes.stream().map(HolidayHarsh::getType).collect(Collectors.joining(","));
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

    public CoordinatesHarsh getCoordinates() { return coordinatesHarsh; }
    public void setCoordinates(CoordinatesHarsh coordinatesHarsh) { this.coordinatesHarsh = coordinatesHarsh; }

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