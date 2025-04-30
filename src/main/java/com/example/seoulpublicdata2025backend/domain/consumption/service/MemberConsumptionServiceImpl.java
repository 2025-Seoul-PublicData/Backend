package com.example.seoulpublicdata2025backend.domain.consumption.service;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyLocationTypeDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.consumption.dao.MemberConsumptionRepository;
import com.example.seoulpublicdata2025backend.domain.consumption.entity.MemberConsumption;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundMemberException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberConsumptionServiceImpl implements MemberConsumptionService {
    private final MemberConsumptionRepository memberConsumptionRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void saveConsumption(CompanyLocationTypeDto companyDto, Long totalPrice) {
        Long kakaoId = SecurityUtil.getCurrentMemberKakaoId();
        Member findMember = memberRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));

        memberConsumptionRepository.findByMemberAndCompanyType(findMember, companyDto.getCompanyType())
                .ifPresentOrElse(
                        existing -> existing.addTotalPrice(totalPrice),
                        () -> memberConsumptionRepository.save(
                                MemberConsumption.builder()
                                        .member(findMember)
                                        .companyType(companyDto.getCompanyType())
                                        .totalPrice(totalPrice)
                                        .build()
                        )
                );
    }
}
