package com.example.card.domain.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name= "eAI_MESSAGE" ,namespace = "urn:esbbank.com/gbo/xml/schemas/v1_0/")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class EaiMessage {

    @XmlElement(name="eAI_HEADER" ,namespace="urn:esbbank.com/gbo/xml/schemas/v1_0/")
    private EaiHeader header;

    @XmlElement(name="eAI_BODY", namespace = "urn:esbbank.com/gbo/xml/schemas/v1_0/")
    private EaiBody body;


}
