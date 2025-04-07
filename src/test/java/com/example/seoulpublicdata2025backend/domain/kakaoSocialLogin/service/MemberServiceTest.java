package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void join() {
        // given
        SignupRequestDto dto = new SignupRequestDto();
        ReflectionTestUtils.setField(dto, "kakaoId", 123456L);
        ReflectionTestUtils.setField(dto, "nickname", "루페온");
        ReflectionTestUtils.setField(dto, "location", "서울");
        ReflectionTestUtils.setField(dto, "role", "CONSUMER");
        ReflectionTestUtils.setField(dto, "profileImageUrl", "http://example.com/image.png");

        // when
        memberService.signup(dto);

        // join
        Member saved = memberRepository.findByKakaoId(123456L).orElseThrow();
        assertEquals("루페온", saved.getNickname());
    }

}