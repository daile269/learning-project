package com.learing.auth_service.service;

import com.learing.auth_service.dto.request.LoginRequest;
import com.learing.auth_service.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
