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
            @RequestHeader(name = AppConstant.UNIT, required = false) String unit,
            @RequestHeader(name = AppConstant.CHANNEL, required = false) String channel,
            @RequestHeader(name = AppConstant.ACCEPT_LANGUAGE,required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID,required = false) String serviceId,
            @RequestHeader(name = AppConstant.SCREENID,required = false) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = false) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = false) String subModuleId,
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
