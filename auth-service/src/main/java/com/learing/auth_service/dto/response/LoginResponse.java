package com.learing.auth_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private boolean authenticated;

//    @JsonProperty("access_token")
//    private String accessToken;
//
//    @JsonProperty("refresh_token")
//    private String refreshToken;
//
//    @JsonProperty("expires_in")
//    private Long expiresIn;
}
