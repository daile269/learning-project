package com.learing.auth_service.client;

import com.learing.auth_service.dto.UserDTO;
import com.learing.auth_service.dto.response.ApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserClient {
    private final WebClient webClient;

    public UserClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8013").build();
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    public UserDTO getUserByUsername(String username) {
        ApiResponse<UserDTO> response = webClient.get()
                .uri("/api/users/username/{username}", username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserDTO>>() {})
                .block();

        if (response != null && response.getCode() == 200) {
            return response.getResult();
        } else {
            throw new RuntimeException("User not found or Server error");
        }
    }

    public UserDTO fallbackUser(String username, Throwable t) {
        System.err.println("Fallback method triggered for user: " + username + " - " + t.getMessage());
        return new UserDTO();  
    }
}
