package com.example.card.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankDetailsNewRequestDto {

    @NotBlank(message = "English name should not be blank")
    @Size(max = 100, message = "English name cannot exceed 100 characters")
    private String nameEn;

    @Size(max = 100, message = "Arabic name cannot exceed 100 characters")
    private String nameAr;

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email format is invalid")
    private String mail;

    @NotNull(message = "Contact number should not be null")
    @Digits(integer = 15, fraction = 0, message = "Contact number must be numeric and up to 15 digits")
    private Long contact;

    @NotBlank(message = "International contact number should not be blank")
    @Pattern(regexp = "^\\+?[0-9\\-\\s]{6,20}$", message = "Invalid international contact format")
    private String internationalContact;

    @Size(max = 255, message = "English URL cannot exceed 255 characters")
    @Pattern(regexp = "^(https?://.*)?$", message = "Invalid English URL format")
    private String urlEn;

    @Size(max = 255, message = "Arabic URL cannot exceed 255 characters")
    @Pattern(regexp = "^(https?://.*)?$", message = "Invalid Arabic URL format")
    private String urlAr;

    @Size(max = 255, message = "Display image URL cannot exceed 255 characters")
    private String displayImage;

    @Positive(message = "Display order must be a positive number")
    private Integer displayOrder;

    @Valid
    private List<@NotNull(message = "FollowUs item cannot be null") FollowUsItemDto> followUs;
}
