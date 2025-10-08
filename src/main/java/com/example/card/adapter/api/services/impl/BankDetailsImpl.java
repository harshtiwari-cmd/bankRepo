package com.example.card.adapter.api.services.impl;

import com.example.card.domain.dto.BankDetailsResponseDto;
import com.example.card.domain.dto.BankDetailsNewRequestDto;
import com.example.card.domain.dto.FollowUsItemDto;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.repository.BankDetailsRepository;
import com.example.card.adapter.api.services.BankDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BankDetailsImpl implements BankDetailsService {

    private final BankDetailsRepository repository;
    private final ObjectMapper objectMapper;

    public BankDetailsImpl(BankDetailsRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public BankDetailsResponseDto getBankDetails() {
        log.info("Fetching latest bank details");

        BankDetailsEntity entity = repository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("No bank details found"));


        BankDetailsResponseDto responseDto = new BankDetailsResponseDto();

        responseDto.setNameEn(entity.getNameEn());
        responseDto.setNameAr(entity.getNameAr());
        responseDto.setMail(entity.getMail());
        responseDto.setContact(entity.getContact());
        responseDto.setInternationalContact(entity.getInternationalContact());
        responseDto.setUrlEn(entity.getUrlEn());
        responseDto.setUrlAr(entity.getUrlAr());
        responseDto.setDisplayImage(entity.getDisplayImage());
        responseDto.setDisplayOrder(entity.getDisplayOrder());

        String followUsJson = entity.getFollowUsJson();
        try {
            List<FollowUsItemDto> followUsItemDtoList = objectMapper.readValue(followUsJson, new TypeReference<>() {});
            responseDto.setFollowUs(followUsItemDtoList);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse followUsJson", e);
            throw new RuntimeException("Invalid followUsJson format");
        }

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
