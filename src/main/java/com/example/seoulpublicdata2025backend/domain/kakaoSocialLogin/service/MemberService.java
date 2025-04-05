package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;

public interface MemberService {

    Member signup(SignupRequestDto dto);
}
