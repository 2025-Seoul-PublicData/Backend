package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoAuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoIdStatusDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import java.util.Optional;

public interface MemberService {

    SignupResponseDto updateMember(SignupRequestDto dto);

    Optional<Member> findByKakaoId(Long kakaoId);

    KakaoAuthResponseDto getMemberStatus(Long kakaoId);

    KakaoIdStatusDto initMember(Long kakaoId);
}
