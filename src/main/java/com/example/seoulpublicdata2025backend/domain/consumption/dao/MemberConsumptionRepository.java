package com.example.seoulpublicdata2025backend.domain.consumption.dao;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.consumption.entity.MemberConsumption;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberConsumptionRepository extends JpaRepository<MemberConsumption, Long> {
    Optional<MemberConsumption> findByMemberAndCompanyType(Member member, CompanyType companyType);
}
