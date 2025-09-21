package com.example.card.services;

import com.example.card.constrants.dto.ServiceBookingRequestDTO;
import com.example.card.constrants.dto.ServiceBookingResponseDTO;

import java.util.List;

public interface ServiceBookingService {

    ServiceBookingResponseDTO createService(ServiceBookingRequestDTO serviceBooking,String serviceId);
    List<ServiceBookingResponseDTO> getServiceByScreenId();
}
