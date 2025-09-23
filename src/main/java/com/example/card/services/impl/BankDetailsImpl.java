package com.example.card.services.impl;

import com.example.card.constrants.mapper.BankDetailsMapper;
import com.example.card.constrants.dto.BankDetailsDto;
import com.example.card.constrants.dto.BankDetailsResponseDto;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.repository.BankDetailsRepository;
import com.example.card.services.BankDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class BankDetailsImpl implements BankDetailsService {

    private final BankDetailsRepository repository;
    private final BankDetailsMapper bankDetailsMapper;

    public BankDetailsImpl(BankDetailsRepository repository, BankDetailsMapper bankDetailsMapper) {
        this.repository = repository;
        this.bankDetailsMapper=bankDetailsMapper;
    }

    @Override
    public BankDetailsEntity createBankDetails(BankDetailsDto dto) {

        if (dto == null || dto.getMail() == null) {
            log.error("Invalid bank details input: dto or email is null");
            throw new ResourceNotFoundException("Account details are required ");
        }

        BankDetailsEntity entity=new BankDetailsEntity();

        entity.setName(dto.getName());
         entity.setMail(dto.getMail());
         entity.setContact(dto.getContact());
         entity.setInternationalContact(dto.getInternationalContact());
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

        BankDetailsResponseDto responseDto = new BankDetailsResponseDto(
                entity.getName(),
                entity.getMail(),
                entity.getContact(),
                entity.getInternationalContact()
        );

        log.debug("Bank details fetched and mapped to response DTO: {}", responseDto);

        return responseDto;
    }


}
