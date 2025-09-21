package com.example.card.constrants.mapper;


import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.entity.AtmEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AtmMapper {

    AtmResponseDto toDto(AtmEntity entity);
}
