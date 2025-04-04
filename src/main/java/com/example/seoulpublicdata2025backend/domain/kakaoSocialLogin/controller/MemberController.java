package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto dto) {

        System.out.println("kakaoId: " + dto.getKakaoId());
        System.out.println("nickname: " + dto.getNickname());
        System.out.println("location: " + dto.getLocation());
        System.out.println("role: " + dto.getRole());

        memberService.signup(dto);
        return ResponseEntity.ok().build();
    }
}
