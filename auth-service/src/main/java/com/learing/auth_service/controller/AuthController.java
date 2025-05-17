package com.learing.auth_service.controller;

import com.learing.auth_service.dto.request.LoginRequest;
import com.learing.auth_service.dto.request.LogoutRequest;
import com.learing.auth_service.dto.request.RefreshTokenRequest;
import com.learing.auth_service.dto.response.LoginResponse;
import com.learing.auth_service.dto.response.RefreshTokenResponse;
import com.learing.auth_service.exception.AppException;
import com.learing.auth_service.exception.ErrorCode;
import com.learing.auth_service.security.JwtService;
import com.learing.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    @DubboReference
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(tokenRequest));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest request) {
        authService.logout(request);
        return ResponseEntity.ok("Logout Success");
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        String token = authHeader.substring(7);
        authService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> tesst() {
        return ResponseEntity.ok("test");
    }
}
