package com.example.card.constrants.dto;

import lombok.Data;

@Data
public class ServiceBookingResponseDTO {
    private Long serviceId;
    private String serviceName;
    private String description;
    private boolean isActive;
    private String screenId;
}
