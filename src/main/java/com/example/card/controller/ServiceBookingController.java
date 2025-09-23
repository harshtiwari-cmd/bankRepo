package com.example.card.controller;

import com.example.card.constrants.dto.GenericResponse;
import com.example.card.constrants.dto.ServiceBookingRequestDTO;
import com.example.card.constrants.dto.ServiceBookingResponseDTO;
import com.example.card.constrants.dto.Status;
import com.example.card.services.ServiceBookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Received request to create service booking for screenId: {}", screenId);

        try {
            ServiceBookingResponseDTO service1 = service.createService(serviceBooking, screenId);
            log.info("service booking created successfully : {}", service1);
            return ResponseEntity.status(HttpStatus.CREATED).body(service1);
        }
        catch (Exception e) {
            log.error("Error occurred while creating service booking for screenId: {}", screenId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<String>>> getAllServicesName() {

        log.info("Received request to fetch all service Names");

        try {
            List<ServiceBookingResponseDTO> responseDTOS = service.getServiceByScreenId();
            AtomicLong counter = new AtomicLong(1);
            List<String> list = responseDTOS
                    .stream()
                    .map(dto -> counter.getAndIncrement() + " - " + dto.getServiceName())
                    .toList();
            if (list.isEmpty()) {
                log.warn("No services found");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GenericResponse<>(new Status("000404", "No Data Found"), new ArrayList<>()));
            }
            log.info("Successfully fetched {} services",list.size());
            GenericResponse<List<String>> response =
                    new GenericResponse<>(new Status("000000", "SUCCESS"), list);

            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error("Error occurred while fetching services: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("G-00001", "Internal Server ERROR"), null));
        }
    }

}
