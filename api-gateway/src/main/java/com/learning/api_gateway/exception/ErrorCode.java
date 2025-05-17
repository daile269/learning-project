package com.learning.api_gateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TOKEN_VALID(401, 401, "Token valid"),
    TOKEN_EXPIRED(401, 401, "TOKEN_EXPIRED"),
    SERVICE_UNAVAILABLE(401, 401, "SERVICE_UNAVAILABLE"),
    TOKEN_INVALIDATED(401, 401, "TOKEN_INVALIDATED");


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
