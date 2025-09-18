package com.example.BankBranches.DTO;

import java.util.List;

public class BankBranchDTO
{
    private String bankName;
    private String bankBranchName;
    private String branchNumber;
    private String description;
    private String bankBranchType;
    private String countryName;
    private String openTime;
    private String closeTime;

    private Coordinates coordinates;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    private List<Holiday> holidayCalendar;
    private List<String> weeklyHolidays;

    public String getBankName() {
        return bankName;
    }

    public List<Holiday> getHolidayCalendar() {
        return holidayCalendar;
    }

    public void setHolidayCalendar(List<Holiday> holidayCalendar) {
        this.holidayCalendar = holidayCalendar;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBankBranchType() {
        return bankBranchType;
    }

    public void setBankBranchType(String bankBranchType) {
        this.bankBranchType = bankBranchType;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public List<String> getWeeklyHolidays() {
        return weeklyHolidays;
    }

    public void setWeeklyHolidays(List<String> weeklyHolidays) {
        this.weeklyHolidays = weeklyHolidays;
    }
}
