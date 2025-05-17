package com.learing.auth_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNAUTHENTICATED(401, 401, "Unauthorized"),
    USER_IS_NOT_EXISTS(404,404,"User not exits"),
    FORBIDDEN(403, 403, "FORBIDDEN"),
    REFRESH_TOKEN_VALID(400, 400, "Refresh token invalid !"),
    TOKEN_INVALID(401, 401, "Token invalid !"),
    TOKEN_EXPIRED(401, 401, "TOKEN_EXPIRED !"),
    REFRESH_TOKEN_EXPIRED(400, 400, "Refresh token expired or malformed!"),
    VALIDLOGIN(400, 400,"Username or password valid" );


    private final int httpStatus;
    private final int code;
    private final String message;

    ErrorCode(int httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.valueOf(httpStatus);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
