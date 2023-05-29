package com.roker.config;

import com.roker.entity.NotificationTypeEnum;
import com.roker.factory.DefaultNotificationServiceFactory;
import com.roker.factory.NotificationServiceFactory;
import com.roker.service.DingTalkService;
import com.roker.service.EmailService;
import com.roker.service.NotificationService;
import com.roker.service.SMSService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 通知自动配置类
 *
 * @author lpy96
 * @date 2023/05/29
 */
@Configuration
@EnableConfigurationProperties(NotificationProperties.class)
public class NotificationAutoConfiguration {

    private final NotificationProperties notificationProperties;

    public NotificationAutoConfiguration(NotificationProperties properties) {
        this.notificationProperties = properties;
    }

    public Map<String, NotificationService> supplierMap;


    /**
     * 初始化
     * 首次加载，初始化缓存通知方式
     */
    @PostConstruct
    public void init() {
        supplierMap = new HashMap<>();
        supplierMap.put(NotificationTypeEnum.SMS.toString(), notificationProperties.getSms() == null ? null : new SMSService(notificationProperties.getSms()));
        supplierMap.put(NotificationTypeEnum.EMAIL.toString(), notificationProperties.getEmail() == null ? null: new EmailService(notificationProperties.getEmail()));
        supplierMap.put(NotificationTypeEnum.DINGTALK.toString(), notificationProperties.getDingtalk() == null ? null: new DingTalkService(notificationProperties.getDingtalk()));
        System.out.println(supplierMap);
    }

    @Bean
    public NotificationServiceFactory notificationServiceFactory() {
        return new DefaultNotificationServiceFactory(supplierMap);
    }

}