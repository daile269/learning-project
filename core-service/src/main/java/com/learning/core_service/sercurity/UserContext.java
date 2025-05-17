package com.learning.core_service.sercurity;

import com.learning.core_service.exception.AppException;
import com.learning.core_service.exception.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class UserContext {

    private String username;
    private String role;

    public void extractUserInfo(ServerWebExchange exchange){
        HttpHeaders headers = exchange.getRequest().getHeaders();
        this.username = headers.getFirst("username");
        this.role = headers.getFirst("role");

        if (this.username == null || this.role == null) {
            throw new AppException(ErrorCode.MISSING_REQUIRED);
        }
    }
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

}
