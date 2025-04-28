package com.learing.auth_service.client;

import com.learing.auth_service.dto.UserDTO;
import com.learing.auth_service.dto.response.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserClient {
    private final WebClient webClient;

    public UserClient(WebClient.Builder builder) {
        //this.webClient = builder.baseUrl("http://host.docker.internal:8013").build();
        this.webClient = builder.baseUrl("http://localhost:8013").build();
    }

    public UserDTO getUserByUsername(String username) {
        ApiResponse<UserDTO> response = webClient.get()
                .uri("/api/users/username/{username}", username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserDTO>>() {})
                .block();

        if (response != null && response.getCode() == 200) {
            return response.getResult();
        } else {
            throw new RuntimeException("Không tìm thấy user hoặc lỗi từ server");
        }
    }

}
