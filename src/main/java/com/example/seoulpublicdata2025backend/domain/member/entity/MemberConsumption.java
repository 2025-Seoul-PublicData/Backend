package com.example.seoulpublicdata2025backend.domain.member.entity;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "member_consumption",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "company_type"})
)
public class MemberConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kakao_Id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Builder
    public MemberConsumption(Member member, CompanyType companyType, Long totalPrice) {
        this.member = member;
        this.companyType = companyType;
        this.totalPrice = totalPrice;
    }

    public void addTotalPrice(Long price) {
        this.totalPrice += price;
    }
}
