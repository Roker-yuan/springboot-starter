package com.roker.config;

import com.roker.properties.DingTalkProperties;
import com.roker.properties.EmailProperties;
import com.roker.properties.SMSProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "notification")
@Data
public class NotificationProperties {

    private SMSProperties sms;
    private EmailProperties email;
    private DingTalkProperties dingtalk;

}