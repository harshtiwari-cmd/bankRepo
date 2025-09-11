package com.example.card.controller;

import com.example.card.constrants.model.ReferenceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reference")
public interface IbNumberGeneration {
    @GetMapping("/generate")
    ResponseEntity<ReferenceResponse> generate(@RequestParam String channel);
}

