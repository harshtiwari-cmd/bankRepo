package com.example.card.constrants.mapper;


import com.example.card.domain.dto.AtmResponseDto;
import com.example.card.domain.dto.CoordinatesDTO;
import com.example.card.constrants.entity.AtmEntity;
import com.example.card.domain.model.Coordinates;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AtmMapper {
    CoordinatesDTO toDto(Coordinates coordinates);

    AtmResponseDto toDto(AtmEntity entity);
}
