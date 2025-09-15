package com.example.card.dto;

import lombok.Data;

import java.util.List;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ServiceBookingRequestDTO {
    @NotBlank(message = "Service name must not be blank")
    private String serviceName;
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    private boolean isActive;
}
