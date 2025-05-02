package com.example.seoulpublicdata2025backend.domain.member.dto;

import com.example.seoulpublicdata2025backend.domain.member.type.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoIdStatusDto {
    private Long kakaoId;
    private MemberStatus status;
}
