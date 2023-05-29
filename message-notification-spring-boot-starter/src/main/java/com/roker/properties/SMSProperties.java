package com.roker.properties;

import lombok.Data;

@Data
public class SMSProperties {
    private String regionId;
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String templateCode;

    // getters and setters
}