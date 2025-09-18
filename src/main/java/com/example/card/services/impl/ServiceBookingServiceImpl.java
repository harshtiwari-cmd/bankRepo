package com.example.card.services.impl;

import com.example.card.constrants.dto.ServiceBookingRequestDTO;
import com.example.card.constrants.dto.ServiceBookingResponseDTO;
import com.example.card.constrants.mapper.ServiceMapper;

import com.example.card.entity.ServiceBooking;
import com.example.card.repository.ServiceBookingRepository;
import com.example.card.services.ServiceBookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceBookingServiceImpl implements ServiceBookingService {

    private ServiceBookingRepository repository;

    private ServiceMapper serviceMapper;

    public ServiceBookingServiceImpl(ServiceBookingRepository repository, ServiceMapper serviceMapper) {
        this.repository = repository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public ServiceBookingResponseDTO createService(ServiceBookingRequestDTO serviceBooking, String screenId) {
        ServiceBooking entity = serviceMapper.toEntity(serviceBooking);
        entity.setScreenId(screenId);
        ServiceBooking save = repository.save(entity);
        return serviceMapper.toDto(save);
    }

    @Override
    public List<ServiceBookingResponseDTO> getServiceByScreenId(String screenId) {
        return repository.findByScreenId(screenId)
                .stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
    }
}
