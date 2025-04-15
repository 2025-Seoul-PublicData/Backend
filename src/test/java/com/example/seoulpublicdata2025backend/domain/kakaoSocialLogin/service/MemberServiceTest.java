package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 성공")
    void join() {
        // given
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .kakaoId(123456L)
                .name("루페온")
                .location("서울")
                .role("CONSUMER")
                .profileImageUrl("http://example.com/image.png")
                .build();

        Member member = Member.create(requestDto);

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        SignupResponseDto response = memberService.signup(requestDto);

        // then
        assertEquals(123456L, response.getMemberId());
    }

}