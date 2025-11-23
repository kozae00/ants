package com.kozae.ants.global.exception;

/**
 * 인증 예외
 */
public class UnauthorizedException extends RuntimeException {
    private final String errorCode;

    public UnauthorizedException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
