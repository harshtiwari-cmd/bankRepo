package com.example.card.adapter.api.services.impl;

import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.domain.dto.BankDetailsNewRequestDto;
import com.example.card.domain.dto.FollowUsItemDto;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.domain.model.SocialMedia;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.repository.BankDetailsRepository;
import com.example.card.adapter.api.services.BankDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@ConditionalOnProperty(name = "mock.enabled", havingValue = "false")
public class BankDetailsImpl implements BankDetailsService {

    @Autowired
    private BankDetailsRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BankDetailsResponseDto getBankDetails(String lang) {
        log.info("Fetching latest bank details");

        BankDetailsEntity entity = repository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("No bank details found"));

        BankDetailsResponseDto responseDto = new BankDetailsResponseDto();
        responseDto.setMail(entity.getMail());
        responseDto.setContact(entity.getContact());
        responseDto.setInternationalContact(entity.getInternationalContact());

        String followUsJson = entity.getFollowUsJson();

        if (followUsJson != null && !followUsJson.isBlank()) {
            try {
                List<FollowUsItemDto> socialLinks = objectMapper.readValue(followUsJson, new TypeReference<>() { });

                List<SocialMedia> socialMedia = mapToSocialMediaList(entity, socialLinks, lang);
                responseDto.setFollowUs(socialMedia);

            } catch (JsonProcessingException e) {
                log.error("Failed to parse followUsJson", e);
                throw new RuntimeException("Invalid followUsJson format", e);
            }
        }
        return responseDto;
    }

    public List<SocialMedia> mapToSocialMediaList(BankDetailsEntity entity, List<FollowUsItemDto> dtoList, String lang) {
        List<SocialMedia> list = new ArrayList<>();

        boolean isEnglish = "en".equalsIgnoreCase(lang);

        for (int i = 0; i < dtoList.size(); i++) {
            FollowUsItemDto dto = dtoList.get(i);
            SocialMedia sm = new SocialMedia();

            sm.setName(isEnglish ? dto.getNameEn() : dto.getNameAr());
            sm.setDisplayImage(dto.getDisplayImage());
            sm.setDisplayOrder(dto.getDisplayOrder());

            // Set URL based on index and language
            switch (i) {
                case 0 -> sm.setUrl(isEnglish ? dto.getInstaUrlEN() : dto.getInstaUrlAR());
                case 1 -> sm.setUrl(isEnglish ? dto.getSnapUrlEN() : dto.getSnapUrlAR());
                case 2 -> sm.setUrl(isEnglish ? dto.getYoutubeUrlEN() : dto.getYoutubeUrlAR());
                case 3 -> sm.setUrl(isEnglish ? dto.getFacebookUrlEN() : dto.getFacebookUrlAR());
                case 4 -> sm.setUrl(isEnglish ? dto.getTwitterUrlEN() : dto.getTwitterUrlAR());
            }

            list.add(sm);
        }

        // Add BankDetailsEntity as a SocialMedia item
        SocialMedia bankMedia = new SocialMedia();
        bankMedia.setName(isEnglish ? entity.getNameEn() : entity.getNameAr());
        bankMedia.setUrl(isEnglish ? entity.getUrlEn() : entity.getUrlAr());
        bankMedia.setDisplayImage(entity.getDisplayImage());
        bankMedia.setDisplayOrder(entity.getDisplayOrder());

        list.add(bankMedia);
        list.sort(Comparator.comparingInt(SocialMedia::getDisplayOrder));
        return list;
    }

    @Override
    public BankDetailsEntity saveBankDetailsNew(BankDetailsNewRequestDto dto) {
        if (dto == null || dto.getMail() == null) {
            throw new ResourceNotFoundException("Account details are required ");
        }

        BankDetailsEntity entity = new BankDetailsEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameAr(dto.getNameAr());
        entity.setMail(dto.getMail());
        entity.setContact(dto.getContact());
        entity.setInternationalContact(dto.getInternationalContact());
        entity.setUrlEn(dto.getUrlEn());
        entity.setUrlAr(dto.getUrlAr());
        entity.setDisplayImage(dto.getDisplayImage());
        entity.setDisplayOrder(dto.getDisplayOrder());

        try {
            entity.setFollowUsJson(objectMapper.writeValueAsString(dto.getFollowUs()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize followUs", e);
        }

        return repository.save(entity);
    }


}