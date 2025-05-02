package com.example.seoulpublicdata2025backend.domain.company.entity;

import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_saves")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MemberCompanySaveId.class)
public class MemberCompanySave {

    @Id
    private Long kakaoId;

    @Id
    private Long companyId;

    @ManyToOne
    @MapsId("kakaoId")
    @JoinColumn(name = "kakao_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Company company;
}
