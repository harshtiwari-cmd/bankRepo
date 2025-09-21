package com.example.card.constrants.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetailsResponseDto {

    @NotNull(message = "name should not be null")
    private String name;
    @Email(message = "email should not be empty")
    private String mail;
    @NotNull(message = "contact should not be null")
    private Long contact;
    @NotNull(message = "international number should not be null")
    private String internationalContact;
}
