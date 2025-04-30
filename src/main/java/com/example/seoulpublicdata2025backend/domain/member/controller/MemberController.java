package com.example.seoulpublicdata2025backend.domain.member.controller;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberConsumptionService;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberService;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.SignUpDocs;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberConsumptionService memberConsumptionService;

    @PostMapping("/signup")
    @SignUpDocs
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto dto) {
        SignupResponseDto response = memberService.updateMember(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consumption")
    public ResponseEntity<List<MemberConsumptionResponseDto>> getMemberConsumption() {
        List<MemberConsumptionResponseDto> response = memberConsumptionService.findConsumptionByMember();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consumption/detail")
    public ResponseEntity<List<MemberConsumptionResponseDto>> getMemberConsumptionDetail(@RequestParam CompanyType companyType) {
        List<MemberConsumptionResponseDto> response = memberConsumptionService.findConsumptionByMemberAndCompanyType(companyType);
        return ResponseEntity.ok(response);
    }
}
