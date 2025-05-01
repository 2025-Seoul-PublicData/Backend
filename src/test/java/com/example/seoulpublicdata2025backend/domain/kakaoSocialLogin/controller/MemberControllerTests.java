package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.MemberService;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtProvider;
import com.example.seoulpublicdata2025backend.global.exception.GlobalExceptionHandler;
import com.example.seoulpublicdata2025backend.global.exception.customException.DuplicationMemberException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class MemberControllerTests {
    @Mock
    MemberService memberService;

    @Mock
    JwtProvider jwtProvider;

    @InjectMocks
    MemberController memberController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp_success() throws Exception {
        SignupRequestDto request = new SignupRequestDto("name","location","CONSUMER","gray");
        SignupResponseDto response = SignupResponseDto.from(-1L, MemberStatus.PRE_MEMBER);

        when(memberService.updateMember(any(SignupRequestDto.class))).thenReturn(response);
        when(jwtProvider.createToken(response.getMemberId(), response.getMemberStatus())).thenReturn("valid-token");

        mockMvc.perform(post("/member/signup")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("access=valid-token")))
                .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("Secure")))
                .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("SameSite=None")));
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 존재하는 유저")
    void signUp_fail_duplicate() throws Exception {

        SignupRequestDto request = new SignupRequestDto("name","location","CONSUMER","gray");

        when(memberService.updateMember(any(SignupRequestDto.class)))
                .thenThrow(new DuplicationMemberException(ErrorCode.DUPLICATE_MEMBER));

        mockMvc.perform(post("/member/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 가입된 사용자입니다."))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.code").value("3000"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.time").exists());
    }

    @Test
    @DisplayName("회원가입 실패 - 요청 값 중 null 있음")
    void signUp_fail_parameter_null() throws Exception {

        SignupRequestDto request = new SignupRequestDto(null,"location","CONSUMER","gray");

        mockMvc.perform(post("/member/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("입력값이 올바르지 않습니다.")) // 에러 메시지 정의에 따라 수정
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.code").value("2000"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").value("name"));
    }
}