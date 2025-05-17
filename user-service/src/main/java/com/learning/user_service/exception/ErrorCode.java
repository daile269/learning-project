package com.learning.user_service.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_IS_EXISTS(400,"User is exits"),
    EMAIL_IS_EXISTS(400,"Email already exists"),
    USER_IS_NOT_EXISTS(400,"User is exits"),
    USERNAME_OR_PASSWORD_VALID(999,"Username or password is valid!"),
    UNAUTHENTICATED(403,"UNAUTHENTICATED" );

    private final int code;
    private final String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
