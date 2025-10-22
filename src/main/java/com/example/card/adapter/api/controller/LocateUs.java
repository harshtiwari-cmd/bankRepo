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


import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/locate-us")
public class LocateUs {

    @Autowired
    private LocateUsService locateUsService;

    private static final Set<String> SUPPORTED_LANGUAGES = AppConstant.SUPPORTED_LANGUAGES;


    @PostMapping
    public ResponseEntity<GenericResponse<List<Map<String, List<Object>>>>> getService(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @Valid @RequestBody(required = true) CardBinAllWrapper wrapper
    ) {
        log.info("GET /locate-us - Received request to fetch all services");

        String language = (lang == null || lang.trim().isEmpty()) ? "en" : lang.trim().toLowerCase();
        if (!SUPPORTED_LANGUAGES.contains(language)) {
            log.warn("Unsupported language received: {}", lang);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse<>(new Status( "000500", "Unsupported language. Use 'ar' or 'en'."), null));
        }

        try {
            CompletableFuture<Map<String, List<LocateUsDTO>>> allTypesFuture = locateUsService.fetchAllTypesAsync(language);
            Map<String, List<LocateUsDTO>> allTypes = allTypesFuture.get();

            List<LocateUsDTO> branches = allTypes.get("branches");
            List<LocateUsDTO> atms = allTypes.get("atms");
            List<LocateUsDTO> kiosks = allTypes.get("kiosks");

            if (branches.isEmpty() && atms.isEmpty() && kiosks.isEmpty()) {
                log.warn("Failed to load: no data found");
                return ResponseEntity.ok(new GenericResponse<>(new Status("000404", "No Data Found"), new ArrayList<>()));
            }

            List<Map<String, List<Object>>> data = new ArrayList<>();

            // Branches list
            List<Object> branchesList = new ArrayList<>();
            branchesList.add(Collections.singletonMap("image", locateUsService.getImageForType("BRANCH")));
            branchesList.addAll(branches);
            data.add(Collections.singletonMap("branches", branchesList));

            // ATMs list
            List<Object> atmsList = new ArrayList<>();
            atmsList.add(Collections.singletonMap("image", locateUsService.getImageForType("ATM")));
            atmsList.addAll(atms);
            data.add(Collections.singletonMap("atms", atmsList));

            // Kiosks list
            List<Object> kiosksList = new ArrayList<>();
            kiosksList.add(Collections.singletonMap("image", locateUsService.getImageForType("KIOSK")));
            kiosksList.addAll(kiosks);
            data.add(Collections.singletonMap("kiosks", kiosksList));

            GenericResponse<List<Map<String, List<Object>>>> response =
                    new GenericResponse<>(new Status("000000", "SUCCESS"), data);
            log.info("Successfully fetched all data");
            return ResponseEntity.ok(response);

        } catch (ExecutionException ee) {
            Throwable cause = ee.getCause();
            String errorCode = "000500";
            String errorMessage = "Internal Server ERROR";
            log.error("Exception occurred while fetching services: {}", cause.getMessage(), cause);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status(errorCode, errorMessage), null));
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted", ie);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("000500", "Operation interrupted"), null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}