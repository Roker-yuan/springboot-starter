package com.roker.properties;

import lombok.Data;

@Data
public class EmailProperties {
    private String host;
    private int port;
    private String from;
    private String to;
    private String username;
    private String password;

}