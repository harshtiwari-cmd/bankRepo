package com.example.card.constrants.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetailsDto {

    @NotNull(message = "name should not be null")
    private String name;

    @Email(message = "email should not be empty")
    private String mail;
    @NotNull(message = "contact should not be null")
    private Long contact;
    @NotNull(message = "international number should not be null")
    private String internationalContact;
    private String instaUrl;
    private String twitterUrl;
    private String fbUrl;
    private String dukhenBankUrl;
    private String snapChatUrl;
    private String youTubeUrl;

}
