package com.example.card.controller.impl;

import com.example.card.constrants.mapper.CardMapper;
import com.example.card.constrants.model.CardRequest;
import com.example.card.constrants.model.CardResponse;
import com.example.card.controller.CardController;
import com.example.card.services.CardValidationService;
import com.example.card.services.StaticResponseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardControllerImpl implements CardController {

    private CardValidationService validationService;

    private StaticResponseService staticResponseService;

    private CardMapper cardMapper;

    public CardControllerImpl(CardValidationService validationService,StaticResponseService staticResponseService, CardMapper cardMapper) {
        this.validationService = validationService;
        this.staticResponseService = staticResponseService;
        this.cardMapper = cardMapper;
    }

    @Override

    public ResponseEntity<CardResponse> validateCard(@RequestBody @Valid CardRequest request) {
        if (!validationService.validate(request)) {
            return ResponseEntity.badRequest().body(staticResponseService.getFailureResponse());
        }
        return ResponseEntity.ok(this.staticResponseService.getSuccessResponse());
    }


}
