package com.learing.notification_service.service;

import com.learing.notification_service.event.UserRegisteredEvent;


public interface NotificationService {
    void sendWelcomeEmail(UserRegisteredEvent userRegisteredEvent);
}
