package com.example.card.domain.dto;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.*;

@Data
@Builder
public class ServiceBookingRequestDTO {
    @NotBlank(message = "Service name must not be blank")
    private String serviceName;
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    private boolean isActive;
}
