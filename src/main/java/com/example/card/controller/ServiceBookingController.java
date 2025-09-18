package com.example.card.controller;


import com.example.card.constrants.dto.ServiceBookingRequestDTO;
import com.example.card.constrants.dto.ServiceBookingResponseDTO;
import com.example.card.services.ServiceBookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-booking")
public class ServiceBookingController {

    private final ServiceBookingService service;

    public ServiceBookingController(ServiceBookingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ServiceBookingResponseDTO> createService(
            @RequestBody @Valid ServiceBookingRequestDTO serviceBooking,
            @RequestParam String screenId) {
        ServiceBookingResponseDTO service1 = service.createService(serviceBooking, screenId);
        return ResponseEntity.status(HttpStatus.CREATED).body(service1);
    }

    @GetMapping
    public ResponseEntity<List<ServiceBookingResponseDTO>> getService(@RequestParam String screenId) {
        try {
            List<ServiceBookingResponseDTO> services = service.getServiceByScreenId(screenId);

            if (services.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
