package com.example.card.adapter.api.controller;

import com.example.card.constrants.mapper.CardMapper;
import com.example.card.domain.model.CardRequest;
import com.example.card.domain.model.CardResponse;
import com.example.card.adapter.api.services.CardValidationService;
import com.example.card.adapter.api.services.StaticResponseService;
import com.example.card.infrastructure.common.AppConstant;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/card")
public class CardController {

    private CardValidationService validationService;

    private StaticResponseService staticResponseService;

    private CardMapper cardMapper;

    public CardController(CardValidationService validationService, StaticResponseService staticResponseService, CardMapper cardMapper) {
        this.validationService = validationService;
        this.staticResponseService = staticResponseService;
        this.cardMapper = cardMapper;
    }

    @PostMapping("/validate")
    public ResponseEntity<CardResponse> validateCard(
            @RequestHeader(name = AppConstant.UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE,required = true) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID,required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @RequestBody @Valid CardRequest request) {

        log.info("POST /card/validate - Received request to validate card number: {}", request.getCardNumber());

        if (!validationService.validate(request)) {
            log.warn("Failed to Validate the Card: {}", request.getCardNumber());
            return ResponseEntity.badRequest().body(staticResponseService.getFailureResponse());
        }
        log.info("Successfully validate the card: {}", request.getCardNumber());
        return ResponseEntity.ok(this.staticResponseService.getSuccessResponse());
    }


}
