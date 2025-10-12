//package com.example.card.adapter.api.controller;
//
//import com.example.card.domain.dto.*;
//import com.example.card.utills.XmlUtil;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/atm")
//@RestController
//public class AtmControllerForXml {
//
//    @PostMapping("/request")
//    public String getAtmRequestXml(
//            @RequestHeader("userId") String userId,
//            @RequestHeader("password") String password,
//            @RequestHeader("serviceName") String serviceName,
//            @RequestHeader("serviceType") String serviceType,
//            @RequestHeader("serviceVersion") String serviceVersion,
//            @RequestHeader("client") String client,
//            @RequestHeader("clientChanel") String clientChanel,
//            @RequestHeader("msgChannel") String msgChannel,
//            @RequestHeader("requestorLanguage") String requestorLanguage,
//            @RequestHeader("referenceNum") String referenceNum,
//            @RequestHeader("locatorType") String locatorType,
//            @RequestHeader("requestTime") String requestTime
//    ) {
//
//        Authentication auth = new Authentication();
//        auth.setUserId(userId);
//        auth.setPassword(password);
//
//        SecurityInfo securityInfo = new SecurityInfo();
//        securityInfo.setAuthentication(auth);
//
//        EaiHeader header = new EaiHeader();
//        header.setServiceName(serviceName);
//        header.setServiceType(serviceType);
//        header.setServiceVersion(serviceVersion);
//        header.setClient(client);
//        header.setClientChanel(clientChanel);
//        header.setMsgChannel(msgChannel);
//        header.setRequestorLanguage(requestorLanguage);
//        header.setSecurityInfo(securityInfo);
//        header.setReturnCode("00000");
//
//        ATMBranchLocationsRequest atmReq = new ATMBranchLocationsRequest();
//        atmReq.setReferenceNum(referenceNum);
//        atmReq.setLocatorType(LocatorType.fromValue(locatorType));
//        atmReq.setRequestTime(requestTime);
//
//        EaiRequest eaiRequest = new EaiRequest();
//        eaiRequest.setAtmBranchLocationsRequest(atmReq);
//
//        EaiBody body = new EaiBody();
//        body.setRequest(eaiRequest);
//
//        EaiMessage message = new EaiMessage();
//        message.setHeader(header);
//        message.setBody(body);
//
//        return XmlUtil.toXml(message);
//    }
//}
