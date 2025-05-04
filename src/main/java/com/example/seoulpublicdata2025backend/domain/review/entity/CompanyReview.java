package com.example.seoulpublicdata2025backend.domain.review.entity;

import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company_reviews")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name="paymentInfo_confirm_num", unique = true)
    private Long paymentInfoConfirmNum;

    @Column(name = "paymentInfo_time")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime paymentInfoTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Company company;

    @Column(name = "company_id")
    private Long companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kakao_id", insertable = false, updatable = false)
    private Member kakao;

    @Column(name = "kakao_id")
    private Long kakaoId;

    private String review;
    private Double temperature;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "company_review_category",
            joinColumns = @JoinColumn(name = "review_id", referencedColumnName = "review_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "review_category")
    private Set<ReviewCategory> reviewCategories = new HashSet<>();


    public void updateReview(String review, Double temperature, Set<ReviewCategory> reviewCategories) {
        this.review = review;
        this.temperature = temperature;
        this.reviewCategories = reviewCategories;
    }
}
