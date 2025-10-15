package com.example.card.adapter.api.controller;


import com.example.card.adapter.api.services.LocateUsService;
import com.example.card.domain.dto.*;
import com.example.card.domain.model.CardBinAllWrapper;
import com.example.card.infrastructure.common.AppConstant;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Slf4j
@RestController
@RequestMapping("/locate-us")
public class LocateUs {

    @Autowired
    private LocateUsService locateUsService;

    private static final Set<String> SUPPORTED_LANGUAGES = Set.of("en", "ar");


    @PostMapping
    public ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> getService(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE, required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID, required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID, required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @Valid @RequestBody(required = true) CardBinAllWrapper wrapper
    ) {
        log.info("GET /locate-us - Received request to fetch all services");

        String language = (lang == null || lang.trim().isEmpty()) ? "en" : lang.trim().toLowerCase();
        if (!SUPPORTED_LANGUAGES.contains(language)) {
            log.warn("Unsupported language received: {}", lang);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse<>(new Status( "G-00000", "Unsupported language. Use 'ar' or 'en'."), null));
        }


        try {
            List<LocateUsDTO> branches;
            List<LocateUsDTO> atms;
            List<LocateUsDTO> kiosks;


                try {
                    branches = locateUsService.fetchByType("BRANCH", language);
                } catch (Exception e) {
                    log.error("Failed to fetch branches: {}", e.getMessage(), e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new GenericResponse<>(new Status("BRANCH_ERROR", "Failed to fetch branches"), null));
                }

                try {
                    atms = locateUsService.fetchByType("ATM", language);
                } catch (Exception e) {
                    log.error("Failed to fetch ATMs: {}", e.getMessage(), e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new GenericResponse<>(new Status("ATM_ERROR", "Failed to fetch ATMs"), null));
                }

                try {
                    kiosks = locateUsService.fetchByType("KIOSK", language);
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
