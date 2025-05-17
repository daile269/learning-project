package com.learning.core_service.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_IS_EXISTS(400,"User is exits"),
    USER_IS_NOT_EXISTS(400,"User is exits"),
    USERNAME_OR_PASSWORD_VALID(400,"Username or password is valid!"),
    POST_NOT_FOUND(400,"Post not found"),
    AUTHOR_NOT_FOUND(400,"Author not found"),
    CANNOT_DELETE_POST(400,"Cannot delete Post because it is not pending delete."),
    CANNOT_RESTORE_POST(400,"Cannot restored Post because it is not pending delete."),
    EMAIL_IS_EXISTS(400,"Email already exists"),

    MISSING_REQUIRED(400,"Missing required headers: username or role"),
    TOKEN_VALID(401, "Token valid or expired!"),
    UNAUTHENTICATED(401,"UNAUTHENTICATED" ),
    FORBIDDEN(403,"FORBIDDEN " );

    private final int code;
    private final String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
