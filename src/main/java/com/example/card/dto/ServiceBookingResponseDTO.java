package com.example.card.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServiceBookingResponseDTO {
    private Long serviceId;
    private String serviceName;
    private String description;
    private boolean isActive;
    private String screenId;
}
