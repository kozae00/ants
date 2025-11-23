package com.kozae.ants.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 토큰 응답 DTO
 */
@Getter
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private String email;
}
