package com.example.card.constrants.dto;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class ServiceBookingRequestDTO {
    @NotBlank(message = "Service name must not be blank")
    private String serviceName;
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    private boolean isActive;
}
