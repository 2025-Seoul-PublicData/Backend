package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoUserInfoResponseDto;

public interface KakaoService {
    String getAccessTokenFromKakao(String code);

    KakaoUserInfoResponseDto getUserInfo(String accessToken);
}
