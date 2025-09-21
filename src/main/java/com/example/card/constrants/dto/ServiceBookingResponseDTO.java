package com.example.card.constrants.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceBookingResponseDTO {
    private Long serviceId;
    private String serviceName;
    private String description;
    private boolean isActive;
    private String screenId;
}
