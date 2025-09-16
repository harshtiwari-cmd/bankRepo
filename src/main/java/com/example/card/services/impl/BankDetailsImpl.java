package com.example.card.services.impl;

import com.example.card.constrants.mapper.BankDetailsMapper;
import com.example.card.dto.BankDetailsDto;
import com.example.card.dto.KioskResponseDTO;
import com.example.card.entity.BankDetailsEntity;
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
        entity.setBankId(dto.getBankId());
         entity.setMail(dto.getMail());
         entity.setContact(dto.getContact());
         entity.setAddress(dto.getAddress());

         return repository.save(entity);

    }

    @Override
    public List<BankDetailsDto> getbankDetails(String bankId) {
        return repository.findByBankId(bankId)
                .stream()
                .map(bankDetailsMapper::toDto)
                .collect(Collectors.toList());
    }

}
