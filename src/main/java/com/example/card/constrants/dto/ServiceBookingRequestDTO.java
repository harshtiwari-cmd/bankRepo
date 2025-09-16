package com.example.card.constrants.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ServiceBookingRequestDTO {
    @NotBlank(message = "Service name must not be blank")
    private String serviceName;
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    private boolean isActive;
}
