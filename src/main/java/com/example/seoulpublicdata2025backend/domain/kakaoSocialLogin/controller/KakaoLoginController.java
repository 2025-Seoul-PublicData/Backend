package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoUserInfoResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/auth/login/kakao")
    public String KakaoLogin(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);


        KakaoUserInfoResponseDto.KakaoAccount.Profile profile = userInfo.kakaoAccount.profile;

        // 여기에 서버 사용자 로그인(인증) 또는 회원가입 로직 추가

        Long kakaoId = userInfo.getId();
        System.out.println(kakaoId);
//        return new ResponseEntity<>(HttpStatus.OK);
        return "redirect:/signup?kakaoId=" + kakaoId;
    }
}
