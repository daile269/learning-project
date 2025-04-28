package com.learing.auth_service.service.Impl;

import com.learing.auth_service.client.UserClient;
import com.learing.auth_service.dto.UserDTO;
import com.learing.auth_service.dto.request.LoginRequest;
import com.learing.auth_service.dto.response.LoginResponse;
import com.learing.auth_service.exception.AppException;
import com.learing.auth_service.exception.ErrorCode;
import com.learing.auth_service.security.JwtService;
import com.learing.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@DubboService
public class AuthServiceImpl implements AuthService {
    private final UserClient userClient;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
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
