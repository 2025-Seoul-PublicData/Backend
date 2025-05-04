package com.example.seoulpublicdata2025backend.domain.member.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {

    private final String name;
    private final String profileColor;

    private AuthResponseDto(String name, String profileColor) {
        this.name = name;
        this.profileColor = profileColor;
    }

    public static AuthResponseDto of(String name, String profileColor) {
        return new AuthResponseDto(name, profileColor);
    }
}
