package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@Import(MemberControllerTest.MockConfig.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public MemberService memberService() {
            return Mockito.mock(MemberService.class);
        }
    }

    @Test
    void 회원가입_API_정상작동() throws Exception {
        String content = """
            {
                "kakaoId": 123456,
                "nickname": "홍길동",
                "location": "서울",
                "role": "CONSUMER",
                "profileImageUrl": "http://example.com/image.png"
            }
            """;

        mockMvc.perform(post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }
}
