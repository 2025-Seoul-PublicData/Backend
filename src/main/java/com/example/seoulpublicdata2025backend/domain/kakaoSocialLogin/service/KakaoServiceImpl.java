package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoTokenResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoUserInfoResponseDto;
import com.example.seoulpublicdata2025backend.global.exception.customException.KakaoApiException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KakaoServiceImpl implements KakaoService{

    private String clientId;
    private final String KAUTH_TOKEN_URL_HOST;
    private final String KAUTH_USER_URL_HOST;

    @Autowired
    public KakaoServiceImpl(@Value("${kakao.auth.client}") String clientId) {
        this.clientId = clientId;
        KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
    }

    @Override
    public String getAccessTokenFromKakao(String code) {

        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new KakaoApiException(ErrorCode.INVALID_KAKAO_REQUEST)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new KakaoApiException(ErrorCode.KAKAO_SERVER_ERROR)))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        log.info(" [Kakao Service] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
        log.info(" [Kakao Service] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());
        log.info(" [Kakao Service] Scope ------> {}", kakaoTokenResponseDto.getScope());

        return kakaoTokenResponseDto.getAccessToken();
    }

    @Override
    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {

        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new KakaoApiException(ErrorCode.INVALID_KAKAO_REQUEST)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new KakaoApiException(ErrorCode.KAKAO_SERVER_ERROR)))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        log.info(" [Kakao Service] Auth ID ------> {}", userInfo.getId());
        log.info(" [Kakao Service] Id Token ------> {}", userInfo.getKakaoAccount().getProfile().getProfileImageUrl());

        return userInfo;
    }

}
