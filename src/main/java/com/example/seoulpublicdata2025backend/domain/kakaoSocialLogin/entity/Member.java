package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    public enum Role{
        CONSUMER, CORPORATE
    }

    public static Member create(SignupRequestDto dto) {
        return Member.builder()
                .kakaoId(dto.getKakaoId())
                .name(dto.getNickname())
                .location(dto.getLocation())
                .role(Member.Role.valueOf(dto.getRole().toUpperCase()))
                .profileImageUrl(dto.getProfileImageUrl())
                .build();
    }

}
