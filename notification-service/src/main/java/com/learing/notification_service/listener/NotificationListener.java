package com.learing.notification_service.listener;

import com.learing.notification_service.event.UserRegisteredEvent;
import com.learing.notification_service.service.NotificationService;
import com.learing.notification_service.untils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "user-registered", groupId = "notification-group")
    public void listenUserRegistration(String event) {
        System.out.println(event);
        try {
            UserRegisteredEvent userRegisteredEvent = JsonUtils.fromJson(event);

            if (userRegisteredEvent != null) {
                System.out.println("Email: " + userRegisteredEvent.getEmail());
                System.out.println("Username: " + userRegisteredEvent.getUsername());
                notificationService.sendWelcomeEmail(userRegisteredEvent);
            } else {
                System.out.println("Failed to deserialize event.");
            }
        } catch (Exception e) {
            System.out.println("Error processing event: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
