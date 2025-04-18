package com.learing.auth_service.client;

import com.learing.auth_service.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserClient {
    private final WebClient webClient;

    public UserClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8013").build();
    }

    public UserDTO getUserByUsername(String username){
        return webClient.get()
                .uri("/api/users/username/{username}",username)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
    }

}
