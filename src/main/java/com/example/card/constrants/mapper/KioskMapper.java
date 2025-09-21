package com.example.card.constrants.mapper;

import com.example.card.constrants.model.Kiosk;
import com.example.card.constrants.dto.KioskRequestDTO;
import com.example.card.constrants.dto.KioskResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KioskMapper {

    Kiosk toEntity(KioskRequestDTO requestDTO);

    KioskResponseDTO toDto(Kiosk entity);

}

