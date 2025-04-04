package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private Long kakaoId;

    private String nickname;
    private String location;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        CONSUMER, CORPORATE
    }
}
