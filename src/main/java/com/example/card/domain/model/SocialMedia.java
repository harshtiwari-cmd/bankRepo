package com.example.card.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialMedia {

    private String name;
    private String url;
    private String displayImage;
    private Integer displayOrder;
}
