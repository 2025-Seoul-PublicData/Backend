package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member signup(SignupRequestDto dto) {
        Member member = Member.builder()
                .kakaoId(dto.getKakaoId())
                .nickname(dto.getNickname())
                .location(dto.getLocation())
                .role(Member.Role.valueOf(dto.getRole().toUpperCase()))
                .profileImageUrl(dto.getProfileImageUrl())
                .build();

        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByKakaoId(Long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId);
    }

}
