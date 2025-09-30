package com.example.card.constrants.mapper;

import com.example.card.domain.model.Kiosk;
import com.example.card.domain.dto.KioskRequestDTO;
import com.example.card.domain.dto.KioskResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KioskMapper {

    Kiosk toEntity(KioskRequestDTO requestDTO);

    KioskResponseDTO toDto(Kiosk entity);

}

