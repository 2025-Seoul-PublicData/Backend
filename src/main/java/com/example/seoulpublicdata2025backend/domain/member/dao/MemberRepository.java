package com.example.seoulpublicdata2025backend.domain.member.dao;

import com.example.seoulpublicdata2025backend.domain.member.dto.AuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoId(Long kakaoId);

    boolean existsByKakaoId(Long kakaoId);

    @Query("SELECT new com.example.seoulpublicdata2025backend.domain.member.dto.AuthResponseDto(" +
            "m.name," +
            "m.location," +
            " m.profileColor) " +
            "FROM Member m WHERE m.kakaoId = :kakaoId")
    Optional<AuthResponseDto> findAuthResponseByKakaoId(Long kakaoId);

    @Query("SELECT m.name FROM Member m WHERE m.kakaoId = :kakaoId")
    Optional<String> findMemberName(Long kakaoId);
}
