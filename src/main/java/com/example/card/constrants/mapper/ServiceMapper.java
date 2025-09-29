package com.example.card.constrants.mapper;

import com.example.card.domain.dto.ServiceBookingRequestDTO;
import com.example.card.domain.dto.ServiceBookingResponseDTO;
import com.example.card.constrants.entity.ServiceBooking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceBooking toEntity(ServiceBookingRequestDTO requestDTO);

    ServiceBookingResponseDTO toDto(ServiceBooking entity);

}
