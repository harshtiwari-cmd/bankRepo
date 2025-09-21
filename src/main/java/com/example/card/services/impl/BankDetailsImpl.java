package com.example.card.services.impl;

import com.example.card.constrants.mapper.BankDetailsMapper;
import com.example.card.constrants.dto.BankDetailsDto;
import com.example.card.constrants.dto.BankDetailsResponseDto;
import com.example.card.constrants.entity.BankDetailsEntity;
import com.example.card.exceptions.ResourceNotFoundException;
import com.example.card.repository.BankDetailsRepository;
import com.example.card.services.BankDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            throw new ResourceNotFoundException("Account details are required ");
        }

        BankDetailsEntity entity=new BankDetailsEntity();

        entity.setName(dto.getName());
         entity.setMail(dto.getMail());
         entity.setContact(dto.getContact());
         entity.setInternationalContact(dto.getInternationalContact());

         return repository.save(entity);

    }

    @Override
    public BankDetailsResponseDto getbankDetails() {
      BankDetailsEntity entity= repository.findTopBy();
      return  new BankDetailsResponseDto(entity.getName(), entity.getMail(), entity.getContact(), entity.getInternationalContact());

    }


}
