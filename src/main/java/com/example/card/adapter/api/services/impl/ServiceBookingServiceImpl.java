package com.example.card.adapter.api.services.impl;

import com.example.card.constrants.mapper.ServiceMapper;
import com.example.card.domain.dto.ServiceBookingRequestDTO;
import com.example.card.domain.dto.ServiceBookingResponseDTO;
import com.example.card.constrants.entity.ServiceBooking;
import com.example.card.repository.ServiceBookingRepository;
import com.example.card.adapter.api.services.ServiceBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

        log.info("Creating new service for screenID: {}", screenId);
        log.debug("Incoming request DTO: {}", serviceBooking);

        try {
            ServiceBooking entity = serviceMapper.toEntity(serviceBooking);
            entity.setScreenId(screenId);
            ServiceBooking save = repository.save(entity);
            log.info("Service booking saved with ID: {}", save.getServiceId());
            return serviceMapper.toDto(save);
        }
        catch (Exception e) {
            log.error("Failed to create service booking for screenId: {}", screenId, e);
            throw e;
        }
    }

    @Override
    public List<ServiceBookingResponseDTO> getServiceByScreenId() {

        log.info("Fetching all service bookings from repository");

        try {
            return repository.findAll()
                    .stream()
                    .map(serviceMapper::toDto)
                    .toList();
        }
       catch (Exception e) {
            log.error("Error while fetching service booking: {}", e.getMessage());
            throw e;
       }
    }
}