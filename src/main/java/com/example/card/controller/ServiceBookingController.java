package com.example.card.controller;

import com.example.card.constrants.dto.GenericResponse;
import com.example.card.constrants.dto.ServiceBookingRequestDTO;
import com.example.card.constrants.dto.ServiceBookingResponseDTO;
import com.example.card.constrants.dto.Status;
import com.example.card.services.ServiceBookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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

//    @GetMapping
//    public ResponseEntity<List<ServiceBookingResponseDTO>> getService() {
//        try {
//            List<ServiceBookingResponseDTO> services = service.getServiceByScreenId();
//
//            if (services.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//            return ResponseEntity.ok(services);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<String>>> getAllServicesName() {
        try {
            List<ServiceBookingResponseDTO> responseDTOS = service.getServiceByScreenId();
            AtomicLong counter = new AtomicLong(1);
            List<String> list = responseDTOS
                    .stream()
                    .map(dto -> counter.getAndIncrement() + " - " + dto.getServiceName())
                    .toList();
            if (list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GenericResponse<>(new Status("000404", "No Data Found"), new ArrayList<>()));
            }
            GenericResponse<List<String>> response =
                    new GenericResponse<>(new Status("000000", "SUCCESS"), list);

            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("G-00001", "Internal Server ERROR"), null));
        }
    }

}
