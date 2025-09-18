package com.example.card.constrants.mapper;



import com.example.card.dto.BankDetailsDto;
import com.example.card.dto.BankDetailsResponseDto;
import com.example.card.entity.BankDetailsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankDetailsMapper {
    BankDetailsEntity toEntity(BankDetailsDto requestDTO);

    BankDetailsResponseDto toDto(BankDetailsEntity entity);
}
