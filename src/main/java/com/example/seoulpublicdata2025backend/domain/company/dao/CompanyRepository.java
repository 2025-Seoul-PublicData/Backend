package com.example.seoulpublicdata2025backend.domain.company.dao;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyId(@Param("companyId") Long companyId);
}
