package com.example.card.adapter.api.controller;


import com.example.card.adapter.api.services.impl.AtmServiceImpl;
import com.example.card.adapter.api.services.impl.BankBranchServiceImpl;
import com.example.card.adapter.api.services.impl.KioskServiceImpl;
import com.example.card.domain.dto.*;
import com.example.card.infrastructure.common.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/locate-us")
public class LocateUs {
    private final AtmServiceImpl atmService;
    private final BankBranchServiceImpl branchService;
    private final KioskServiceImpl kioskService;

    public LocateUs(AtmServiceImpl atmService, BankBranchServiceImpl branchService, KioskServiceImpl kioskService) {
        this.kioskService = kioskService;
        this.branchService = branchService;
        this.atmService = atmService;

    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> getService(
            @RequestHeader(name = AppConstant.UNIT, required = false) String unit,
            @RequestHeader(name = AppConstant.CHANNEL, required = false) String channel,
            @RequestHeader(name = AppConstant.ACCEPT_LANGUAGE,required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = false) String serviceId,
            @RequestHeader(name = AppConstant.SCREENID,required = false) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = false) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = false) String subModuleId
    ) {
        log.info("GET /locate-us - Received request to fetch all services");
        try {
            List<BankBranchDTO> branches;
            List<AtmResponseDto> atms;
            List<KioskResponseDTO> kiosks;

            try {
                branches = branchService.getAllBranchesWithStatus();
            } catch (Exception e) {
                log.error("Failed to fetch branches: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new GenericResponse<>(new Status("BRANCH_ERROR", "Failed to fetch branches"), null));
            }

            try {
                atms = atmService.getAtm();
            } catch (Exception e) {
                log.error("Failed to fetch ATMs: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new GenericResponse<>(new Status("ATM_ERROR", "Failed to fetch ATMs"), null));
            }

            try {
                kiosks = kioskService.getKiosk();
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
