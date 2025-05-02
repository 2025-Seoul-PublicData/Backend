package com.example.seoulpublicdata2025backend.domain.company.dao;

import com.example.seoulpublicdata2025backend.domain.company.entity.MemberCompanySave;
import com.example.seoulpublicdata2025backend.domain.company.entity.MemberCompanySaveId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCompanySaveRepository extends JpaRepository<MemberCompanySave, MemberCompanySaveId> {

    List<MemberCompanySave> findByKakaoId(Long kakaoId);
    Long countByKakaoId(Long kakaoId);
    boolean existsByKakaoIdAndCompanyId(Long kakaoId, Long companyId);
}
