package com.example.seoulpublicdata2025backend.domain.support.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "consumer_support_products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsumerSupportProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description", columnDefinition = "TEXT")
    private String productDescription;

    @Column(name = "product_link", columnDefinition = "TEXT")
    private String productLink;

    @Column(name = "product_type", length = 20)
    private String productType;

    private String benefit;

    private String method;

    private String period;

    @Builder
    public ConsumerSupportProduct(String bankName, String productName, String productDescription, String productLink,
                                  String productType, String benefit, String method, String period) {
        this.bankName = bankName;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productLink = productLink;
        this.productType = productType;
        this.benefit = benefit;
        this.method = method;
        this.period = period;
    }
}
