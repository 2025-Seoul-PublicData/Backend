package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoAuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member signup(SignupRequestDto dto) {
        return memberRepository.save(Member.create(dto));
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
