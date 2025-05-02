package com.example.seoulpublicdata2025backend.domain.member.controller;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.member.util.CookieFactory;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberConsumptionService;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberService;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtProvider;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.GetMeberConsumptionDetailDocs;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.GetMemberConsumptionDocs;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.SignUpDocs;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final MemberConsumptionService memberConsumptionService;

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

    @GetMapping("/consumption")
    @GetMemberConsumptionDocs
    public ResponseEntity<List<MemberConsumptionResponseDto>> getMemberConsumption() {
        List<MemberConsumptionResponseDto> response = memberConsumptionService.findConsumptionByMember();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consumption/detail")
    @GetMeberConsumptionDetailDocs
    public ResponseEntity<MemberConsumptionResponseDto> getMemberConsumptionDetail(@RequestParam CompanyType companyType) {
        MemberConsumptionResponseDto response = memberConsumptionService.findConsumptionByMemberAndCompanyType(companyType);
        return ResponseEntity.ok(response);
    }
}
