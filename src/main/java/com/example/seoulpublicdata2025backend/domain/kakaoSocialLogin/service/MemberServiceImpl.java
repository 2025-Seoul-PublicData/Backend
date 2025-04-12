package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoAuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import com.example.seoulpublicdata2025backend.global.exception.customException.AuthenticationException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public SignupResponseDto signup(SignupRequestDto dto) {
        Member member = Member.create(dto);
        try {
            Member savedMember = memberRepository.save(member);
            return SignupResponseDto.from(savedMember.getKakaoId());
        } catch (DataIntegrityViolationException exception) {
            throw new AuthenticationException(ErrorCode.DUPLICATE_MEMBER);
        }
    }

    @Override
    public Optional<Member> findByKakaoId(Long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId);
    }

    @Override
    public KakaoAuthResponseDto getMemberStatus(Long kakaoId) {
        boolean result = memberRepository.existsByKakaoId(kakaoId);
        if (result) {
            return KakaoAuthResponseDto.of(MemberStatus.MEMBER);
        }
        return KakaoAuthResponseDto.of(MemberStatus.NOT_MEMBER);
    }

}
