package com.learning.user_service.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_IS_EXISTS(999,"User is exits"),
    USER_IS_NOT_EXISTS(999,"User is exits"),
    USERNAME_OR_PASSWORD_VALID(969,"Username or password is valid!"),
    UNAUTHENTICATED(777,"UNAUTHENTICATED" );

    private final int code;
    private final String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
