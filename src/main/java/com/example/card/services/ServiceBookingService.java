package com.example.card.services;

import com.example.card.dto.KioskResponseDTO;
import com.example.card.dto.ServiceBookingRequestDTO;
import com.example.card.dto.ServiceBookingResponseDTO;
import com.example.card.entity.ServiceBooking;

import java.util.List;

public interface ServiceBookingService {

    ServiceBookingResponseDTO createService(ServiceBookingRequestDTO serviceBooking,String serviceId);
    List<ServiceBookingResponseDTO> getServiceByScreenId(String screenId);
}
