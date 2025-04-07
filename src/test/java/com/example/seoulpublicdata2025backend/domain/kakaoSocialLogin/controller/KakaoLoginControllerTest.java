package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoUserInfoResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.KakaoService;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.MemberService;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KakaoLoginController.class)
@Import(KakaoLoginControllerTest.MockConfig.class)
class KakaoLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KakaoService kakaoService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public KakaoService kakaoService() {
            return Mockito.mock(KakaoService.class);
        }

        @Bean
        public MemberService memberService() {
            return Mockito.mock(MemberService.class);
        }

        @Bean
        public JwtProvider jwtProvider() {
            return Mockito.mock(JwtProvider.class);
        }
    }

    @Test
    void 카카오_로그인_성공_후_리다이렉트() throws Exception {
        // given
        String fakeAccessToken = "fake-access-token";

        KakaoUserInfoResponseDto.KakaoAccount.Profile profile = new KakaoUserInfoResponseDto.KakaoAccount.Profile();
        profile.setProfileImageUrl("http://example.com/profile.png");

        KakaoUserInfoResponseDto.KakaoAccount account = new KakaoUserInfoResponseDto.KakaoAccount();
        account.profile = profile;

        KakaoUserInfoResponseDto userInfo = new KakaoUserInfoResponseDto();
        userInfo.setId(123456L);
        userInfo.kakaoAccount = account;

        // when
        Mockito.when(kakaoService.getAccessTokenFromKakao(anyString())).thenReturn(fakeAccessToken);
        Mockito.when(kakaoService.getUserInfo(fakeAccessToken)).thenReturn(userInfo);

        // then
        mockMvc.perform(get("/auth/login/kakao").param("code", "dummy-code"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signup?kakaoId=123456&profileUrl=http://example.com/profile.png"));
    }
}
