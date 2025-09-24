package com.example.card.service.impl;

import com.example.card.constrants.dto.ServiceBookingRequestDTO;
import com.example.card.constrants.dto.ServiceBookingResponseDTO;
import com.example.card.constrants.entity.ServiceBooking;
import com.example.card.constrants.mapper.ServiceMapper;
import com.example.card.repository.ServiceBookingRepository;
import com.example.card.services.impl.ServiceBookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceBookingServiceImplTest {

    private ServiceBookingRepository repository;
    private ServiceMapper serviceMapper;
    private ServiceBookingServiceImpl serviceBookingService;

    @BeforeEach
    void setUp() {
        repository = mock(ServiceBookingRepository.class);
        serviceMapper = mock(ServiceMapper.class);
        serviceBookingService = new ServiceBookingServiceImpl(repository, serviceMapper);
    }

    @Test
    void createService_ShouldSaveAndReturnDto() {
        // Arrange
        ServiceBookingRequestDTO request = ServiceBookingRequestDTO.builder()
                .serviceName("Test Service")
                .description("Test Description")
                .isActive(true)
                .build();

        ServiceBooking entity = new ServiceBooking();
        entity.setServiceName(request.getServiceName());
        entity.setDescription(request.getDescription());
        entity.setActive(request.isActive());
        entity.setScreenId("SCR001");

        ServiceBooking savedEntity = new ServiceBooking();
        savedEntity.setServiceId(1L);
        savedEntity.setServiceName(entity.getServiceName());
        savedEntity.setDescription(entity.getDescription());
        savedEntity.setActive(entity.isActive());
        savedEntity.setScreenId(entity.getScreenId());

        ServiceBookingResponseDTO expectedResponse = ServiceBookingResponseDTO.builder()
                .serviceId(savedEntity.getServiceId())
                .serviceName(savedEntity.getServiceName())
                .description(savedEntity.getDescription())
                .screenId(savedEntity.getScreenId())
                .isActive(savedEntity.isActive()) // important for matching test
                .build();

        when(serviceMapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(serviceMapper.toDto(savedEntity)).thenReturn(expectedResponse);

        // Act
        ServiceBookingResponseDTO response = serviceBookingService.createService(request, "SCR001");

        // Assert
        assertNotNull(response);
        assertEquals("Test Service", response.getServiceName());
        assertEquals("Test Description", response.getDescription());
        assertEquals("SCR001", response.getScreenId());
        assertTrue(response.isActive()); // matches builder field

        // Verify save called
        verify(repository, times(1)).save(entity);
    }

    @Test
    void getServiceByScreenId_ShouldReturnDtoList() {
        ServiceBooking entity = new ServiceBooking();
        entity.setServiceId(1L);
        entity.setServiceName("Test Service");
        entity.setDescription("Test Description");
        entity.setActive(true);
        entity.setScreenId("SCR001");

        ServiceBookingResponseDTO dto = ServiceBookingResponseDTO.builder()
                .serviceId(1L)
                .serviceName("Test Service")
                .description("Test Description")
                .screenId("SCR001")
                .isActive(true)
                .build();

        when(repository.findAll()).thenReturn(Collections.singletonList(entity));
        when(serviceMapper.toDto(entity)).thenReturn(dto);

        List<ServiceBookingResponseDTO> result = serviceBookingService.getServiceByScreenId();

        assertEquals(1, result.size());
        assertEquals("Test Service", result.get(0).getServiceName());
        assertTrue(result.get(0).isActive());
    }
}
