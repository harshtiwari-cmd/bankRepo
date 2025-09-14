package com.example.card.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServiceBookingRequestDTO {

    private String serviceName;
    private String description;
    private boolean isActive;
    private String location;
    private String durationMinutes;
    private List<String> availableDays;
}
