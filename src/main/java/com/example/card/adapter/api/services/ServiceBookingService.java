package com.example.card.adapter.api.services;

import com.example.card.domain.dto.ServiceBookingRequestDTO;
import com.example.card.domain.dto.ServiceBookingResponseDTO;

import java.util.List;

public interface ServiceBookingService {

    ServiceBookingResponseDTO createService(ServiceBookingRequestDTO serviceBooking,String serviceId);
    List<ServiceBookingResponseDTO> getServiceByScreenId();
}
