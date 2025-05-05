package com.example.seoulpublicdata2025backend.domain.member.controller;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.member.dto.AuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.util.CookieFactory;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberConsumptionService;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberService;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReview;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtProvider;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.GetMeberConsumptionDetailDocs;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.GetMemberConsumptionDocs;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.LogoutDocs;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.SignUpDocs;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    @SignUpDocs
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDto dto) {
        SignupResponseDto signupResponseDto = memberService.updateMember(dto);
        String updatedToken = jwtProvider.createToken(signupResponseDto.getMemberId(),
                signupResponseDto.getMemberStatus());
        // 쿠키 생성
        ResponseCookie cookie = CookieFactory.createCookie("access", updatedToken);

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .build();
    }

    @PostMapping("/logout")
    @LogoutDocs
    public ResponseEntity<Void> logout() {
        ResponseCookie deleteCookie = CookieFactory.deleteCookie("access");

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
    }

    @GetMapping("/auth/me")
    public ResponseEntity<AuthResponseDto> getAuthMe() {
        AuthResponseDto memberAuth = memberService.getMemberAuth();
        return ResponseEntity.ok(memberAuth);
    }
}
