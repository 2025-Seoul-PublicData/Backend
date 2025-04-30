package com.example.seoulpublicdata2025backend.domain.review.entity;

import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "company_reviews")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(CompanyReviewId.class)
public class CompanyReview {

    @Id
    @Column(name="paymentInfo_confirm_num")
    private Long paymentInfoConfirmNum;

    @Id
    @Column(name = "paymentInfo_time")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime paymentInfoTime;

    @ManyToOne
//    @JoinColumn(name = "company_id", referencedColumnName = "companyId")
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "kakao_Id", referencedColumnName = "kakaoId", insertable = false, updatable = false)
    private Member kakao;

    @Column(name = "kakao_id")
    private Long kakaoId;

    private String review;
    private Double temperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_category")
    private ReviewCategory reviewCategory;

    public void updateReview(String review, Double temperature, ReviewCategory reviewCategory) {
        this.review = review;
        this.temperature = temperature;
        this.reviewCategory = reviewCategory;
    }
}
