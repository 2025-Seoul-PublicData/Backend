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

    private static final String MOBILE_BASEURL = "http://172.16.21.135:5173";
    private static final String LOCAL_BASEURL = "http://localhost:5173";
    private static final String REFERER = "Referer";

    @GetMapping("/auth/login/kakao")
    @KakaoLoginCheckDocs
    public ResponseEntity<Void> checkMemberSignUp(
            @RequestParam("code") String code,
            HttpServletRequest request
    ) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        Long kakaoId = userInfo.getId();

        KakaoIdStatusDto kakaoIdStatusDto = memberService.initMember(kakaoId);
        String token = jwtProvider.createToken(kakaoIdStatusDto);

        List<ResponseCookie> cookies = createCookies(token, kakaoId);
        String baseUrl = getBaseUrl(request);

        HttpHeaders headers = setHttpHeaders(cookies, kakaoIdStatusDto, baseUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    private static HttpHeaders setHttpHeaders(
            List<ResponseCookie> cookies, KakaoIdStatusDto kakaoIdStatusDto, String baseUrl
    ) {
        HttpHeaders headers = new HttpHeaders();
        for (ResponseCookie cookie : cookies) {
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        if (kakaoIdStatusDto.getStatus().equals(MemberStatus.MEMBER)) {
            String redirectUrl = UriComponentsBuilder.fromUriString(baseUrl)
                    .build()
                    .toUriString();
            headers.setLocation(URI.create(redirectUrl));
        } else if (kakaoIdStatusDto.getStatus().equals(MemberStatus.PRE_MEMBER)) {
            String redirectUrl = UriComponentsBuilder.fromUriString(baseUrl + "/signup")
                    .build()
                    .toUriString();
            headers.setLocation(URI.create(redirectUrl));
        }
        return headers;
    }

    private static String getBaseUrl(HttpServletRequest request) {
        String referer = request.getHeader(REFERER);

        String baseUrl;
        if (referer != null && referer.contains("172.16.21.135")) {
            baseUrl = MOBILE_BASEURL;
        } else {
            baseUrl = LOCAL_BASEURL;
        }
        return baseUrl;
    }

    private static List<ResponseCookie> createCookies(String token, Long kakaoId) {
        ResponseCookie accessCookie = ResponseCookie.from("access", token)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(2))
                .build();

        ResponseCookie kakaoIdCookie = ResponseCookie.from("kakaoId", kakaoId.toString())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(2))
                .build();

        return List.of(accessCookie, kakaoIdCookie);
    }

}
