package com.example.card.constrants.mapper;



import com.example.card.domain.dto.FXRateResponseDto;
import com.example.card.constrants.entity.FxRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FxRateMapper {
    FXRateResponseDto toDto(FxRate entity);
}
