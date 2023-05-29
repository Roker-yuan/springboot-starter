package com.roker.factory;

import com.roker.config.NotificationAutoConfiguration;
import com.roker.entity.NotificationTypeEnum;
import com.roker.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class DefaultNotificationServiceFactory implements NotificationServiceFactory {
    public Map<String, NotificationService> supplierMap;

    public DefaultNotificationServiceFactory(Map<String, NotificationService> supplierMap){
        this.supplierMap = supplierMap;
    };


    @Override
    public NotificationService create(NotificationTypeEnum typeEnum) {
        String type = typeEnum.toString().toUpperCase();
        NotificationService supplier = supplierMap.get(type);
        if (supplier == null) {
            throw new IllegalArgumentException("Invalid notification type: " + type);
        }
        return supplier;
    }

}