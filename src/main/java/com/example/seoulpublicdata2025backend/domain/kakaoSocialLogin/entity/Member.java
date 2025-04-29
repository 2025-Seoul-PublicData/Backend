package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
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

    public void update(SignupRequestDto dto) {
        this.name = dto.getName();
        this.location = dto.getLocation();
        this.profileColor = dto.getProfileColor();
        this.role = Member.Role.valueOf(dto.getRole().toUpperCase());
        this.status = MemberStatus.MEMBER;
    }

    public static Member init(Long kakaoId) {
        return Member.builder()
                .kakaoId(kakaoId)
                .status(MemberStatus.PRE_MEMBER)
                .build();
    }

}
