package com.example.card.controller;

import com.example.card.constrants.model.CardRequest;
import com.example.card.constrants.model.CardResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/card")
public interface CardController {
    @PostMapping("/validate")
    ResponseEntity<CardResponse> validateCard(@RequestBody CardRequest request);
}
