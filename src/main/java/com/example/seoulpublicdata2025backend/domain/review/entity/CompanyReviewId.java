package com.example.seoulpublicdata2025backend.domain.review.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class CompanyReviewId implements Serializable {

    @EqualsAndHashCode.Include
    private Long paymentInfoConfirmNum;

    @EqualsAndHashCode.Include
    private LocalDateTime paymentInfoTime;
}
