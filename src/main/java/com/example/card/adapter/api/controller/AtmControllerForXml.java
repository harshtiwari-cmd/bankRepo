package com.example.card.adapter.api.controller;


import com.example.card.domain.dto.*;
import com.example.card.utills.XmlUtil;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/atm")
@RestController
public class AtmControllerForXml {

    @PostMapping("/request")
    public String getAtmRequestXml()
    {

        Authentication auth=new Authentication();
        auth.setUserId("userId");
        auth.setPassword("Password");

        SecurityInfo securityInfo=new SecurityInfo();
        securityInfo.setAuthentication(auth);

        EaiHeader header=new EaiHeader();
        header.setServiceName("ATM.LOCATIONS");
        header.setServiceType("SYNC");
        header.setServiceVersion("1");
        header.setClient("BARW");
        header.setClientChanel("MOB");
        header.setMsgChannel("MQ");
        header.setRequestorLanguage("E");
        header.setSecurityInfo(securityInfo);
        header.setReturnCode("00000");


        ATMBranchLocationsRequest atmReq=new ATMBranchLocationsRequest();
        atmReq.setReferenceNum("AB7926262622");
        atmReq.setLocatorType(LocatorType.fromValue("ATM"));
        atmReq.setRequestTime("2025-10-02T12:30:00");


        EaiRequest eaiRequest=new EaiRequest();
        eaiRequest.setAtmBranchLocationsRequest(atmReq);

        EaiBody body=new EaiBody();
        body.setRequest(eaiRequest);



        EaiMessage message=new EaiMessage();
        message.setHeader(header);
        message.setBody(body);

        return XmlUtil.toXml(message);


    }





}
