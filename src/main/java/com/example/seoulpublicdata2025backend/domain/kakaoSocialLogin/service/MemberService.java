package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signup(SignupRequestDto dto) {
        Member member = Member.builder()
                .kakaoId(dto.getKakaoId())
                .nickname(dto.getNickname())
                .location(dto.getLocation())
                .role(Member.Role.valueOf(dto.getRole().toUpperCase()))
                .build();

        return memberRepository.save(member);
    }
}
