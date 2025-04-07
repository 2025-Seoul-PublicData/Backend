package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoUserInfoResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.KakaoService;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.MemberService;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @GetMapping("/auth/login/kakao")
    public String KakaoLogin(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        KakaoUserInfoResponseDto.KakaoAccount.Profile profile = userInfo.kakaoAccount.profile;

        // 여기에 서버 사용자 로그인(인증) 또는 회원가입 로직 추가

        Long kakaoId = userInfo.getId();
        String profileUrl = profile.getProfileImageUrl();

        Optional<Member> existingMember = memberService.findByKakaoId(kakaoId);

        if (existingMember.isPresent()) {

            String token = jwtProvider.createToken(String.valueOf(kakaoId));
            log.info("JWT Token: {}", token);
            return "redirect:/main";
        } else {
            return "redirect:/signup?kakaoId=" + kakaoId + "&profileUrl=" + profileUrl;
        }

    }
}
