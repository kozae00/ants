package com.kozae.ants.domain.member.service;

import com.kozae.ants.domain.member.dto.*;
import com.kozae.ants.domain.member.dto.MemberLoginRequest;
import com.kozae.ants.domain.member.dto.MemberResponse;
import com.kozae.ants.domain.member.dto.MemberSignupRequest;
import com.kozae.ants.domain.member.dto.TokenResponse;
import com.kozae.ants.domain.member.entity.Member;
import com.kozae.ants.domain.member.repository.MemberRepository;
import com.kozae.ants.global.exception.BusinessException;
import com.kozae.ants.global.exception.UnauthorizedException;
import com.kozae.ants.global.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 Service
 */
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 회원가입
     */
    public MemberResponse signup(MemberSignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("EMAIL_ALREADY_EXISTS", "이미 존재하는 이메일입니다");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .build();

        Member savedMember = memberRepository.save(member);
        return new MemberResponse(savedMember);
    }

    /**
     * 로그인
     */
    public TokenResponse login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다"));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new UnauthorizedException("INVALID_PASSWORD", "비밀번호가 일치하지 않습니다");
        }

        String token = jwtUtil.generateToken(member.getEmail());
        return new TokenResponse(token, member.getEmail());
    }

    /**
     * 회원 조회
     */
    @Transactional(readOnly = true)
    public MemberResponse getMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다"));
        return new MemberResponse(member);
    }
}
