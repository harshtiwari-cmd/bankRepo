package com.example.card.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class FollowUsItemDto {

    private String instaUrlEN;
    private String instaUrlAR;

    private String snapUrlEN;
    private String snapUrlAR;

    private String youtubeUrlEN;
    private String youtubeUrlAR;

    private String facebookUrlEN;
    private String facebookUrlAR;

    private String twitterUrlEN;
    private String twitterUrlAR;

    private String displayImage;
    private Integer displayOrder;

    private String nameEn;
    private String nameAr;
}


