package com.example.card.constrants.mapper;


import com.example.card.constrants.dto.AtmResponseDto;
import com.example.card.constrants.dto.CoordinatesDTO;
import com.example.card.constrants.entity.AtmEntity;
import com.example.card.constrants.model.Coordinates;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AtmMapper {
    CoordinatesDTO toDto(Coordinates coordinates);
    AtmResponseDto toDto(AtmEntity entity);
}
