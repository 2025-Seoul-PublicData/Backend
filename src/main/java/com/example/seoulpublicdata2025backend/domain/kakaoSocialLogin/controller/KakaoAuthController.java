package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoIdStatusDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoUserInfoResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.KakaoService;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.MemberService;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtProvider;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.KakaoLoginCheckDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private static final String FRONT_BASEURL = "https://morak.vercel.app";

    @GetMapping("/auth/login/kakao")
    @KakaoLoginCheckDocs
    public ResponseEntity<Void> checkMemberSignUp(
            @RequestParam("code") String code
    ) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        Long kakaoId = userInfo.getId();

        KakaoIdStatusDto kakaoIdStatusDto = memberService.initMember(kakaoId);
        String token = jwtProvider.createToken(kakaoIdStatusDto);
        List<ResponseCookie> cookies = createCookies(token, kakaoId);
        HttpHeaders headers = setHttpHeaders(cookies, kakaoIdStatusDto);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    private static HttpHeaders setHttpHeaders(
            List<ResponseCookie> cookies, KakaoIdStatusDto kakaoIdStatusDto
    ) {
        HttpHeaders headers = new HttpHeaders();
        for (ResponseCookie cookie : cookies) {
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        if (kakaoIdStatusDto.getStatus().equals(MemberStatus.MEMBER)) {
            String redirectUrl = UriComponentsBuilder.fromUriString(FRONT_BASEURL)
                    .build()
                    .toUriString();
            headers.setLocation(URI.create(redirectUrl));
        } else if (kakaoIdStatusDto.getStatus().equals(MemberStatus.PRE_MEMBER)) {
            String redirectUrl = UriComponentsBuilder.fromUriString(FRONT_BASEURL + "/signup")
                    .build()
                    .toUriString();
            headers.setLocation(URI.create(redirectUrl));
        }
        return headers;
    }

    private static List<ResponseCookie> createCookies(String token, Long kakaoId) {
        ResponseCookie accessCookie = ResponseCookie.from("access", token)
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .secure(true)
                .maxAge(Duration.ofHours(2))
                .build();

        ResponseCookie kakaoIdCookie = ResponseCookie.from("kakaoId", kakaoId.toString())
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .secure(true)
                .maxAge(Duration.ofHours(2))
                .build();

        return List.of(accessCookie, kakaoIdCookie);
    }

}
