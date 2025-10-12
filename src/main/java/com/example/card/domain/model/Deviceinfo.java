package com.example.card.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Deviceinfo {

    private String deviceId;
    private String ipAddress;
    private String vendorId;
    private String osVersion;
    private String osType;
    private String appVersion;
    private String endToEndId;

}
