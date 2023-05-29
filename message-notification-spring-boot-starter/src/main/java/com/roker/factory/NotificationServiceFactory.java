package com.roker.factory;

import com.roker.entity.NotificationTypeEnum;
import com.roker.service.NotificationService;

public interface NotificationServiceFactory {
    NotificationService create(NotificationTypeEnum typeEnum);
}