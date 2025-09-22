package com.example.card.controller;



import com.example.card.constrants.dto.*;
import com.example.card.services.impl.AtmServiceImpl;
import com.example.card.services.impl.BankBranchServiceImpl;
import com.example.card.services.impl.KioskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/locate-us")
public class LocateUs {
    private final AtmServiceImpl atmService;
    private final BankBranchServiceImpl branchService;
    private final KioskServiceImpl kioskService;

    public LocateUs(AtmServiceImpl atmService,BankBranchServiceImpl branchService,KioskServiceImpl kioskService) {
        this.kioskService = kioskService;
        this.branchService = branchService;
        this.atmService = atmService;

    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<Map<String, List<?>>>>> getService() {
        try {
            List<BankBranchDTO> branches = branchService.getAllBranchesWithStatus();
            List<AtmResponseDto> atms = atmService.getAtm();
            List<KioskResponseDTO> kiosks = kioskService.getKiosk();

            if (branches.isEmpty() && atms.isEmpty() && kiosks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GenericResponse<>(new Status("000404", "No Data Found"), new ArrayList<>()));
            }

            List<Map<String, List<?>>> data = new ArrayList<>();
            data.add(Collections.singletonMap("branches", branches));
            data.add(Collections.singletonMap("atms", atms));
            data.add(Collections.singletonMap("kiosks", kiosks));

            GenericResponse<List<Map<String, List<?>>>> response =
                    new GenericResponse<>(new Status("000000", "SUCCESS"), data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(new Status("G-00001", "Internal Server ERROR"), null));
        }
    }
}
