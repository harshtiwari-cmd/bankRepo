package com.example.card.adapter.api.controller;


import com.example.card.adapter.api.services.LocateUsService;
import com.example.card.domain.dto.*;
import com.example.card.domain.model.Deviceinfo;
import com.example.card.infrastructure.common.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/locate-us")
public class LocateUs {
    private final LocateUsService locateUsService;

    public LocateUs(LocateUsService locateUsService) {
        this.locateUsService = locateUsService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> getService(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREENID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @RequestBody Deviceinfo request
            ) {
        log.info("GET /locate-us - Received request to fetch all services");
        try {
            List<LocateUsDTO> branches;
            List<LocateUsDTO> atms;
            List<LocateUsDTO> kiosks;

            try {
                branches = locateUsService.fetchByType("BRANCH");
            } catch (Exception e) {
                log.error("Failed to fetch branches: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new GenericResponse<>(new Status("BRANCH_ERROR", "Failed to fetch branches"), null));
            }

            try {
                atms = locateUsService.fetchByType("ATM");
            } catch (Exception e) {
                log.error("Failed to fetch ATMs: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new GenericResponse<>(new Status("ATM_ERROR", "Failed to fetch ATMs"), null));
            }

            try {
                kiosks = locateUsService.fetchByType("KIOSK");
            } catch (Exception e) {
                log.error("Failed to fetch kiosks: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new GenericResponse<>(new Status("KIOSK_ERROR", "Failed to fetch kiosks"), null));
            }

            if (branches.isEmpty() && atms.isEmpty() && kiosks.isEmpty()) {
                log.warn("Failed to load: no data found");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GenericResponse<>(new Status("000404", "No Data Found"), new ArrayList<>()));
            }

            List<Map<String, List<?>>> data = new ArrayList<>();
            data.add(Collections.singletonMap("branches", branches));
            data.add(Collections.singletonMap("atms", atms));
            data.add(Collections.singletonMap("kiosks", kiosks));

            GenericResponse<List<Map<String, List<?>>>> response =
                    new GenericResponse<>(new Status("000000", "SUCCESS"), data);

            log.info("Successfully fetched all data");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception occurred while fetching services: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("G-00001", "Internal Server ERROR"), null));
        }
    }
}
