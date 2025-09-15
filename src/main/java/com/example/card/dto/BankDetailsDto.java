package com.example.card.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetailsDto {

    @Email(message = "email should not be empty")
    private String mail;
    @NotNull(message = "contact should not be null")
    private Long contact;
    @NotNull(message = "address should not be null")
    private String address;

}
