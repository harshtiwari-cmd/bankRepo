package com.example.card.adapter.api.controller;

import com.example.card.domain.dto.ATMBranchLocationsReply;
import com.example.card.domain.dto.ATMBranchLocationsRequest;
import com.example.card.domain.dto.LocatorType;
import com.example.card.domain.dto.ReturnStatus;
import com.example.card.utills.XmlUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/atm")
@RestController
public class AtmControllerForXml {

    @PostMapping(value = "/reply", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getReply(
            @RequestHeader(value = "referenceNum", required = false) String referenceNum,
            @RequestHeader(value = "locatorType", required = false) String locatorType,
            @RequestHeader(value = "requestTime", required = false) String requestTime
    ) {
        ATMBranchLocationsRequest request = new ATMBranchLocationsRequest();
        request.setReferenceNum(referenceNum);
        request.setLocatorType(LocatorType.fromValue(locatorType));
        request.setRequestTime(requestTime);

        String xml = XmlUtil.toXml(request);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(xml);
    }


}
