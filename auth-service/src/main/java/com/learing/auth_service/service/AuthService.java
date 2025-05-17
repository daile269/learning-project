package com.learing.auth_service.service;

import com.learing.auth_service.dto.request.LoginRequest;
import com.learing.auth_service.dto.request.LogoutRequest;
import com.learing.auth_service.dto.request.RefreshTokenRequest;
import com.learing.auth_service.dto.response.LoginResponse;
import com.learing.auth_service.dto.response.RefreshTokenResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(LogoutRequest logoutRequest);
    boolean validateToken(String token);
}
