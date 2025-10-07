package com.example.card.adapter.api.services.impl;

import com.example.card.constrants.mapper.BankDetailsMapper;
import com.example.card.domain.dto.BankDetailsDto;
import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.domain.dto.BankDetailsNewRequestDto;
import com.example.card.domain.dto.FollowUsItemDto;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.repository.BankDetailsRepository;
import com.example.card.adapter.api.services.BankDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BankDetailsImpl implements BankDetailsService {

    private final BankDetailsRepository repository;
    private final BankDetailsMapper bankDetailsMapper;

    public BankDetailsImpl(BankDetailsRepository repository, BankDetailsMapper bankDetailsMapper) {
        this.repository = repository;
        this.bankDetailsMapper = bankDetailsMapper;
    }

    @Override
    public BankDetailsEntity createBankDetails(BankDetailsDto dto) {

        if (dto == null || dto.getMail() == null) {
            log.error("Invalid bank details input: dto or email is null");
            throw new ResourceNotFoundException("Account details are required ");
        }

        BankDetailsEntity entity = new BankDetailsEntity();

        entity.setName(dto.getName());
        entity.setMail(dto.getMail());
        entity.setContact(dto.getContact());
        entity.setInternationalContact(dto.getInternationalContact());
        entity.setFbUrl(dto.getFbUrl());
        entity.setDukhenBankUrl(dto.getDukhenBankUrl());
        entity.setTwitterUrl(dto.getTwitterUrl());
        entity.setInstaUrl(dto.getInstaUrl());
        entity.setSnapChatUrl(dto.getSnapChatUrl());
        entity.setYouTubeUrl(dto.getYouTubeUrl());
        log.debug("Mapped DTO to entity: {}", entity);

        BankDetailsEntity savedEntity = repository.save(entity);
        log.info("Bank details saved successfully with ID: {}", savedEntity.getId());

        return savedEntity;

    }

    @Override
    public BankDetailsResponseDto getbankDetails() {
        log.info("Fetching latest bank details");

        BankDetailsEntity entity = repository.findTopBy();

        if (entity == null) {
            log.warn("No bank details found in repository");
            throw new ResourceNotFoundException("No bank details found");
        }

        BankDetailsResponseDto responseDto = new BankDetailsResponseDto();
        responseDto.setNameEn(entity.getNameEn() != null ? entity.getNameEn() : entity.getName());
        responseDto.setNameAr(entity.getNameAr() != null ? entity.getNameAr() : entity.getName());
        responseDto.setMail(entity.getMail());
        responseDto.setContact(entity.getContact());
        responseDto.setInternationalContact(entity.getInternationalContact());
        responseDto.setUrlEn(entity.getUrlEn() != null ? entity.getUrlEn() : entity.getDukhenBankUrl());
        responseDto.setUrlAr(entity.getUrlAr() != null ? entity.getUrlAr() : entity.getDukhenBankUrl());

        java.util.List<FollowUsItemDto> followUs = new java.util.ArrayList<>();

        // If new JSON field present, prefer it
        if (entity.getFollowUsJson() != null && !entity.getFollowUsJson().isEmpty()) {
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                java.util.List<FollowUsItemDto> fromDb = mapper.readValue(
                        entity.getFollowUsJson(),
                        mapper.getTypeFactory().constructCollectionType(java.util.List.class, FollowUsItemDto.class)
                );
                followUs.addAll(fromDb);
            } catch (Exception e) {
                log.warn("Failed to deserialize followUsJson, falling back to legacy URLs", e);
            }
        }

        if (followUs.isEmpty() && entity.getInstaUrl() != null) {
            FollowUsItemDto insta = new FollowUsItemDto();
            insta.setInstaUrlEN(entity.getInstaUrl());
            insta.setInstaUrlAR(entity.getInstaUrl());
            insta.setDisplayImage("/images/insta.png");
            insta.setDisplayOrder(1);
            insta.setNameEn("instagram");
            insta.setNameAr("instagram");
            followUs.add(insta);
        }

        if (followUs.isEmpty() && entity.getSnapChatUrl() != null) {
            FollowUsItemDto snap = new FollowUsItemDto();
            snap.setSnapUrlEN(entity.getSnapChatUrl());
            snap.setSnapUrlAR(entity.getSnapChatUrl());
            snap.setDisplayImage("/images/snap.png");
            snap.setDisplayOrder(2);
            snap.setNameEn("snapchat");
            snap.setNameAr("snapchat");
            followUs.add(snap);
        }

        if (followUs.isEmpty() && entity.getYouTubeUrl() != null) {
            FollowUsItemDto yt = new FollowUsItemDto();
            yt.setYoutubeUrlEN(entity.getYouTubeUrl());
            yt.setYoutubeUrlAR(entity.getYouTubeUrl());
            yt.setDisplayImage("/images/youtube.png");
            yt.setDisplayOrder(3);
            yt.setNameEn("youtube");
            yt.setNameAr("youtube");
            followUs.add(yt);
        }

        if (followUs.isEmpty() && entity.getFbUrl() != null) {
            FollowUsItemDto fb = new FollowUsItemDto();
            fb.setFacebookUrlEN(entity.getFbUrl());
            fb.setFacebookUrlAR(entity.getFbUrl());
            fb.setDisplayImage("/images/facebook.png");
            fb.setDisplayOrder(4);
            fb.setNameEn("facebook");
            fb.setNameAr("facebook");
            followUs.add(fb);
        }

        if (followUs.isEmpty() && entity.getTwitterUrl() != null) {
            FollowUsItemDto tw = new FollowUsItemDto();
            tw.setTwitterUrlEN(entity.getTwitterUrl());
            tw.setTwitterUrlAR(entity.getTwitterUrl());
            tw.setDisplayImage("/images/twitter.png");
            tw.setDisplayOrder(5);
            tw.setNameEn("twitter");
            tw.setNameAr("twitter");
            followUs.add(tw);
        }

        responseDto.setFollowUs(followUs);
        responseDto.setDisplayOrder(0);

        log.debug("Bank details fetched and mapped to response DTO: {}", responseDto);

        return responseDto;
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
        entity.setDisplayOrder(dto.getDisplayOrder());

        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            entity.setFollowUsJson(dto.getFollowUs() != null ? mapper.writeValueAsString(dto.getFollowUs()) : null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize followUs", e);
        }

        BankDetailsEntity saved = repository.save(entity);
        return saved;
    }

}
