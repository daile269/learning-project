package com.learning.user_service.producer;

import com.learning.user_service.dto.request.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationPublisher {

    private final StreamBridge streamBridge;

    public void publishUserRegisteredEvent(UserRegisteredEvent event){
        streamBridge.send("userRegistrationOutput",event);
    }
}
