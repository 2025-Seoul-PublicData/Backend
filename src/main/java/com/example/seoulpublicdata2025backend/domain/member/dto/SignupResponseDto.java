package com.example.seoulpublicdata2025backend.domain.member.dto;

import com.example.seoulpublicdata2025backend.domain.member.type.MemberStatus;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private final Long memberId;
    private final MemberStatus memberStatus;

    private SignupResponseDto(Long memberId, MemberStatus memberStatus) {
        this.memberId = memberId;
        this.memberStatus = memberStatus;
    }

    public static SignupResponseDto from(Long memberId, MemberStatus memberStatus) {
        return new SignupResponseDto(memberId, memberStatus);
    }
}
