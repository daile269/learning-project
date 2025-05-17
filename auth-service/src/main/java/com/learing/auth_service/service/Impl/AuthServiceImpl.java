package com.learing.auth_service.service.Impl;

import com.learing.auth_service.client.UserClient;
import com.learing.auth_service.dto.UserDTO;
import com.learing.auth_service.dto.request.LoginRequest;
import com.learing.auth_service.dto.request.LogoutRequest;
import com.learing.auth_service.dto.request.RefreshTokenRequest;
import com.learing.auth_service.dto.response.LoginResponse;
import com.learing.auth_service.dto.response.RefreshTokenResponse;
import com.learing.auth_service.entity.InvalidatedToken;
import com.learing.auth_service.exception.AppException;
import com.learing.auth_service.exception.ErrorCode;
import com.learing.auth_service.repository.InvalidatedTokenRepository;
import com.learing.auth_service.security.JwtService;
import com.learing.auth_service.service.AuthService;
import com.learing.auth_service.service.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@DubboService
public class AuthServiceImpl implements AuthService {


    private final UserClient userClient;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final MyUserDetailsService userDetailsService;

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    public LoginResponse login(LoginRequest loginRequest){
        UserDTO user = userClient.getUserByUsername(loginRequest.getUsername());
        if(user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.VALIDLOGIN);
        }
        String tokenResponse = jwtService.generateToken(loginRequest.getUsername());
        String refreshTokenResponse = jwtService.generateRefreshToken(loginRequest.getUsername());
        return LoginResponse.builder()
                .accessToken(tokenResponse)
                .refreshToken(refreshTokenResponse)
                .authenticated(true).build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        try{
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtService.validateToken(token,userDetails)){
                return RefreshTokenResponse.builder()
                        .token(jwtService.generateToken(username))
                        .build();
            }else {
                throw new AppException(ErrorCode.REFRESH_TOKEN_VALID);
            }
        }catch (Exception e) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        String token = logoutRequest.getToken();
        Date expiryDate = jwtService.extractExpiration(token);
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .token(token)
                .expirationDate(expiryDate.toInstant())
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }

    @Override
    public boolean validateToken(String token) {
        try {

            if (isTokenInvalidated(token)) {
                throw new AppException(ErrorCode.TOKEN_INVALID);
            }

            if (jwtService.isTokenExpired(token)) {
                throw new AppException(ErrorCode.TOKEN_EXPIRED);
            }

            String username = jwtService.extractUsername(token);
            if (username == null || username.isBlank()) {
                throw new AppException(ErrorCode.TOKEN_INVALID);
            }

        } catch (ExpiredJwtException e) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        return false;
    }
    private boolean isTokenInvalidated(String token) {
        return invalidatedTokenRepository.findByToken(token) != null;
    }
//    @Value("${keycloak.token-uri}")
//    private String tokenUri;
//
//    @Value("${keycloak.client-id}")
//    private String clientId;
//
//    @Value("${keycloak.client-secret}")
//    private String clientSecret;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Override
//    public LoginResponse login(LoginRequest request){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
//        map.add("grant_type", "password");
//        map.add("client_id", clientId);
//        map.add("client_secret", clientSecret);
//        map.add("username", request.getUsername());
//        map.add("password", request.getPassword());
//
//        HttpEntity<MultiValueMap<String,String >> entity = new HttpEntity<>(map,headers);
//        ResponseEntity<LoginResponse> response = restTemplate.exchange(
//                tokenUri, HttpMethod.POST,entity, LoginResponse.class
//        );
//        return response.getBody();
//    }
}
