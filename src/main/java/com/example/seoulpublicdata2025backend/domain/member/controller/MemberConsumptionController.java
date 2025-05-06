package com.example.seoulpublicdata2025backend.domain.member.controller;

import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionDetailResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberConsumptionService;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberService;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.GetMeberConsumptionDetailDocs;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.member.GetMemberConsumptionDocs;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/consumption")
public class MemberConsumptionController {

    private final MemberService memberService;
    private final MemberConsumptionService memberConsumptionService;
    private final ReviewService reviewService;

    @GetMapping
    @GetMemberConsumptionDocs
    public ResponseEntity<MemberConsumptionResponseDto> getMemberConsumption() {
        List<MemberConsumptionDto> consumptions = memberConsumptionService.findConsumptionByMember();
        String memberName = memberService.findMemberName();
        Long reviewCount = reviewService.getCountMemberReview();
        MemberConsumptionResponseDto response = MemberConsumptionResponseDto.builder()
                .consumptions(consumptions)
                .name(memberName)
                .reviewCount(reviewCount)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail")
    @GetMeberConsumptionDetailDocs
    public ResponseEntity<MemberConsumptionDetailResponseDto> getMemberConsumptionDetail(@Valid @ModelAttribute MemberConsumptionRequestDto dto) {
        MemberConsumptionDto consumption = memberConsumptionService.findConsumptionByMemberAndCompanyType(dto);
        String memberName = memberService.findMemberName();
        Long reviewCount = reviewService.getCountMemberReviewByType(dto);
        MemberConsumptionDetailResponseDto response = MemberConsumptionDetailResponseDto.builder()
                .consumption(consumption)
                .name(memberName)
                .reviewCount(reviewCount)
                .build();
        return ResponseEntity.ok(response);
    }
}
