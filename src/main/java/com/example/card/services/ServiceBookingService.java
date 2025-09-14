package com.example.card.services;

import com.example.card.dto.ServiceBookingRequestDTO;
import com.example.card.dto.ServiceBookingResponseDTO;
import com.example.card.entity.ServiceBooking;

public interface ServiceBookingService {

    ServiceBookingResponseDTO createService(ServiceBookingRequestDTO serviceBooking);
}
