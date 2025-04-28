package com.learing.notification_service.service.impl;

import com.learing.notification_service.event.UserRegisteredEvent;
import com.learing.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;
    @Override
    public void sendWelcomeEmail(UserRegisteredEvent event) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(event.getEmail());
        mailMessage.setSubject("Welcome "+event.getUsername()+" to our Service");
        mailMessage.setText("Thank you for registering with our service!");
        mailSender.send(mailMessage);
        System.out.println("Sending welcome email to :" +event.getEmail());

    }
}
