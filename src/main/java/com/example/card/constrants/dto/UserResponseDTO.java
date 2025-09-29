package com.example.card.constrants.dto;

import com.example.card.constrants.model.Address;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String name;
    private String card;
    private Address address;
}
