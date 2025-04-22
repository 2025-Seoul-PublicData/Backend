package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoAuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoUserInfoResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.KakaoService;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.MemberService;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(KakaoAuthController.class)
//@Import(KakaoAuthControllerTest.MockConfig.class)
class KakaoAuthControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private KakaoService kakaoService;
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private JwtProvider jwtProvider;
//
//    @TestConfiguration
//    static class MockConfig {
//        @Bean
//        public KakaoService kakaoService() {
//            return Mockito.mock(KakaoService.class);
//        }
//
//        @Bean
//        public MemberService memberService() {
//            return Mockito.mock(MemberService.class);
//        }
//        @Bean
//        public JwtProvider jwtProvider() {
//            return Mockito.mock(JwtProvider.class);
//        }
//    }

    @Test
    void 카카오_로그인_성공_후_리다이렉트() throws Exception {
//        // given
//        String fakeAccessToken = "fake-access-token";
//        String fakeCode = "fake-code";
//        Long fakeId = -1L;
//
//        KakaoUserInfoResponseDto.KakaoAccount.Profile profile = new KakaoUserInfoResponseDto.KakaoAccount.Profile();
//        profile.setProfileImageUrl("http://example.com/profile.png");
//
//        KakaoUserInfoResponseDto.KakaoAccount account = new KakaoUserInfoResponseDto.KakaoAccount();
//        account.profile = profile;
//
//        KakaoUserInfoResponseDto fakeUserInfo = new KakaoUserInfoResponseDto();
//        fakeUserInfo.setId(fakeId);
//        fakeUserInfo.kakaoAccount = account;
//
//        KakaoAuthResponseDto dto = KakaoAuthResponseDto.of(MemberStatus.MEMBER);
//
//        // when - mocking services
//        Mockito.when(kakaoService.getAccessTokenFromKakao(fakeCode)).thenReturn(fakeAccessToken);
//        Mockito.when(kakaoService.getUserInfo(fakeAccessToken)).thenReturn(fakeUserInfo);
//        Mockito.when(memberService.getMemberStatus(fakeId)).thenReturn(dto);
//
//
//        // then
//        mockMvc.perform(MockMvcRequestBuilders.get("/auth/login/kakao")
//                        .param("code", fakeCode)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.memberStatus").value(MemberStatus.MEMBER.getValue())) // 응답 타입에 따라 다르게 작성
//                .andDo(print());
    }
}

