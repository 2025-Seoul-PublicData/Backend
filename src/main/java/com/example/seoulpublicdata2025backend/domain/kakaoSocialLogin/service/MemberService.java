package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;

import java.util.Optional;

public interface MemberService {

    Member signup(SignupRequestDto dto);

    Optional<Member> findByKakaoId(Long kakaoId);
}
