package com.example.card.controller;

import com.example.card.dto.ServiceBookingRequestDTO;
import com.example.card.dto.ServiceBookingResponseDTO;
import com.example.card.entity.ServiceBooking;
import com.example.card.services.ServiceBookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-booking")
public class ServiceBookingController {

    private final ServiceBookingService service;

    public ServiceBookingController(ServiceBookingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ServiceBookingResponseDTO> createService(@RequestBody @Valid ServiceBookingRequestDTO serviceBooking) {
        ServiceBookingResponseDTO service1 = service.createService(serviceBooking);
        return ResponseEntity.status(HttpStatus.CREATED).body(service1);
    }
}
