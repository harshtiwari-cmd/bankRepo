package com.example.card.domain.dto;

import com.example.card.domain.model.Address;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String name;
    private String card;
    private Address address;
}
