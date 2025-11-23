package com.kozae.ants.domain.member.controller;

import com.ants.domain.member.dto.*;
import com.kozae.ants.domain.member.dto.MemberLoginRequest;
import com.kozae.ants.domain.member.dto.MemberResponse;
import com.kozae.ants.domain.member.dto.MemberSignupRequest;
import com.kozae.ants.domain.member.dto.TokenResponse;
import com.kozae.ants.domain.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 Controller
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@Valid @RequestBody MemberSignupRequest request) {
        MemberResponse response = memberService.signup(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody MemberLoginRequest request) {
        TokenResponse response = memberService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMe(Authentication authentication) {
        String email = authentication.getName();
        MemberResponse response = memberService.getMember(email);
        return ResponseEntity.ok(response);
    }
}
