package com.example.seoulpublicdata2025backend.domain.member.service;

import com.example.seoulpublicdata2025backend.domain.member.dto.KakaoUserInfoResponseDto;

public interface KakaoService {
    String getAccessTokenFromKakao(String code);

    KakaoUserInfoResponseDto getUserInfo(String accessToken);
}
