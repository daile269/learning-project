package com.learing.auth_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNAUTHENTICATED(401, 401, "Unauthorized"),
    USER_IS_NOT_EXISTS(404,999,"User not exits"),
    FORBIDDEN(403, 403, "FORBIDDEN"),
    VALIDLOGIN(444, 444,"Username or password valid" );


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
