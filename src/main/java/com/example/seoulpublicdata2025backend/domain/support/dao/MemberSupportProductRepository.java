package com.example.seoulpublicdata2025backend.domain.support.dao;

import com.example.seoulpublicdata2025backend.domain.support.entity.MemberSupportProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSupportProductRepository extends JpaRepository<MemberSupportProduct, Long> {
}
