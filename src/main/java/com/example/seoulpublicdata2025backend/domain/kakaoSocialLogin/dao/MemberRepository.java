package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoId(Long kakaoId);
}
