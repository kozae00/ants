package com.kozae.ants.domain.member.dto;

import com.kozae.ants.domain.member.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 회원 응답 DTO
 */
@Getter
public class MemberResponse {
    private Long id;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.createdAt = member.getCreatedAt();
    }
}
