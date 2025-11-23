package com.kozae.ants.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 에러 응답 DTO
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String message;
}
