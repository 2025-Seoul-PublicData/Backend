package com.example.seoulpublicdata2025backend.domain.member.service;

import com.example.seoulpublicdata2025backend.domain.member.dto.KakaoAuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.KakaoIdStatusDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {

    SignupResponseDto updateMember(SignupRequestDto dto);

    Optional<Member> findByKakaoId(Long kakaoId);

    KakaoAuthResponseDto getMemberStatus(Long kakaoId);

    KakaoIdStatusDto initMember(Long kakaoId);
}
