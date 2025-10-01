package com.example.card.constrants.mapper;

import com.example.card.domain.dto.ProfitRateRequestDTO;
import com.example.card.domain.dto.ProfitRateResponseDTO;
import com.example.card.constrants.entity.ProfitRate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfitRateMapper {

    ProfitRate toEntity(ProfitRateRequestDTO requestDTO);

    List<ProfitRateResponseDTO> toDto(List<ProfitRate> profitRate);

    ProfitRateResponseDTO toDto(ProfitRate profitRate);
}
