package com.example.card.constrants.mapper;

import com.example.card.constrants.model.Kiosk;
import com.example.card.dto.KioskRequestDTO;
import com.example.card.dto.KioskResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KioskMapper {

    Kiosk toEntity(KioskRequestDTO requestDTO);

    KioskResponseDTO toDto(Kiosk entity);

}

