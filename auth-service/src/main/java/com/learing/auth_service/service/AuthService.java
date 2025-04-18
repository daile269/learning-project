package com.learing.auth_service.service;

import com.learing.auth_service.client.UserClient;
import com.learing.auth_service.dto.UserDTO;
import com.learing.auth_service.dto.request.LoginRequest;
import com.learing.auth_service.dto.response.LoginResponse;
import com.learing.auth_service.exception.AppException;
import com.learing.auth_service.exception.ErrorCode;
import com.learing.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserClient userClient;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest){
        UserDTO user = userClient.getUserByUsername(loginRequest.getUsername());
        if(user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.VALIDLOGIN);
        }
        String tokenResponse = jwtService.generateToken(loginRequest.getUsername());
        return LoginResponse.builder()
                .token(tokenResponse)
                .authenticated(true).build();
    }
}
