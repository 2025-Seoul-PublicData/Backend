package com.example.seoulpublicdata2025backend.domain.geocoding.dao;

import com.example.seoulpublicdata2025backend.domain.geocoding.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GeocodingRepository extends JpaRepository<Company, String> {
    Optional<Company> findByCompanyName(@Param("companyName") String companyName);
}
