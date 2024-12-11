package com.example.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    private final String errorCode;
    public UnauthorizedException(String message) {
        super(message); this.errorCode = "PROSELYTE_UNAUTHORIZED";
    }
    public UnauthorizedException(String message, String errorCode) {
        super(message); this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode;
    }
}
