package com.example.card.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowUsItemDto {

    private String nameEn;
    private String nameAr;

    private String urlEn;
    private String urlAr;

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


}


