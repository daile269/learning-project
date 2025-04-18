package com.learing.auth_service.controller;

import com.learing.auth_service.dto.request.LoginRequest;
import com.learing.auth_service.dto.response.LoginResponse;
import com.learing.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/test")
    public String he(){
        return "HEllo";
    }
}
