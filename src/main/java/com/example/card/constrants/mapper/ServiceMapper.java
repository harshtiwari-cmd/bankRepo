package com.example.card.constrants.mapper;

import com.example.card.dto.ServiceBookingRequestDTO;
import com.example.card.dto.ServiceBookingResponseDTO;
import com.example.card.entity.ServiceBooking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceBooking toEntity(ServiceBookingRequestDTO requestDTO);

    ServiceBookingResponseDTO toDto(ServiceBooking entity);

}
