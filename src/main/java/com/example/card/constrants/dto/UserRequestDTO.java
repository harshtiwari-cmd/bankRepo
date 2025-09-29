package com.example.card.constrants.dto;

import com.example.card.constrants.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserRequestDTO {


    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Card number is mandatory")
    @Pattern(regexp = "\\d{15}", message = "Card number must be 15 digits")
    private String card;

    private Address address;
}
