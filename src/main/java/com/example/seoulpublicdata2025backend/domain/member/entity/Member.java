package com.example.seoulpublicdata2025backend.domain.member.entity;

import com.example.seoulpublicdata2025backend.domain.member.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.UpdateMemberRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.type.MemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="members")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private Long kakaoId;

    private String name;
    private String location;

    @Column(name = "profile_color")
    private String profileColor;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        CONSUMER, CORPORATE
    }

    public void completeSignup(SignupRequestDto dto) {
        this.name = dto.getName();
        this.location = dto.getLocation();
        this.profileColor = dto.getProfileColor();
        this.role = Member.Role.valueOf(dto.getRole().toUpperCase());
        this.status = MemberStatus.MEMBER;
    }

    public void update(UpdateMemberRequestDto dto) {
        this.name = dto.getName();
        this.location = dto.getLocation();
        this.profileColor = dto.getProfileColor();
    }

    public static Member init(Long kakaoId) {
        return Member.builder()
                .kakaoId(kakaoId)
                .status(MemberStatus.PRE_MEMBER)
                .build();
    }

}
