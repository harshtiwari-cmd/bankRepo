package com.example.card.services.impl;

import com.example.card.constrants.mapper.ServiceMapper;
import com.example.card.dto.ServiceBookingRequestDTO;
import com.example.card.dto.ServiceBookingResponseDTO;
import com.example.card.entity.ServiceBooking;
import com.example.card.repository.ServiceBookingRepository;
import com.example.card.services.ServiceBookingService;
import org.springframework.stereotype.Service;

@Service
public class ServiceBookingServiceImpl implements ServiceBookingService {

    private ServiceBookingRepository repository;

    private ServiceMapper serviceMapper;

    public ServiceBookingServiceImpl(ServiceBookingRepository repository, ServiceMapper serviceMapper) {
        this.repository = repository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public ServiceBookingResponseDTO createService(ServiceBookingRequestDTO serviceBooking) {

        ServiceBooking entity = serviceMapper.toEntity(serviceBooking);

        ServiceBooking save = repository.save(entity);

        return serviceMapper.toDto(save);
    }
}
