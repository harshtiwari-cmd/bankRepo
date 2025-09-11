package com.example.card.constrants.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CardRequest {
    @NotBlank(message = "Card number cannot be blank")
    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    private String cardNumber;

    @NotBlank(message = "Card holder name cannot be blank")
    private String cardHolderName;
}
