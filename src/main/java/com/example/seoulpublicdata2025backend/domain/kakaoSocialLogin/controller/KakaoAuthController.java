package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoAuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoIdStatusDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoUserInfoResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.KakaoService;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.MemberService;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtProvider;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.KakaoLoginCheckDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "카카오 로그인 API")
public class KakaoAuthController {

    private final KakaoService kakaoService;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Value("${frontend.redirect.base-url}")
    private String frontendBaseUrl;

    @GetMapping("/auth/login/kakao")
    @KakaoLoginCheckDocs
    public ResponseEntity<Void> checkMemberSignUp(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        Long kakaoId = userInfo.getId();
        KakaoIdStatusDto kakaoIdStatusDto = memberService.initMember(kakaoId);
        String token = jwtProvider.createToken(kakaoIdStatusDto);

        // JWT를 쿠키에 담기
        ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", token)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofHours(1))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.SET_COOKIE, cookie.toString());
        String redirectUrl = UriComponentsBuilder.fromUriString(frontendBaseUrl + "/signup")
                .queryParam("kakaoId", kakaoId)
                .build()
                .toUriString();

        headers.setLocation(URI.create(redirectUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
