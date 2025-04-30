package com.example.seoulpublicdata2025backend.domain.member.service;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyLocationTypeDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.member.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.domain.member.dao.MemberConsumptionRepository;
import com.example.seoulpublicdata2025backend.domain.member.entity.MemberConsumption;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundMemberException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<MemberConsumptionResponseDto> findConsumptionByMember() {
        Long kakaoId = SecurityUtil.getCurrentMemberKakaoId();
        List<MemberConsumption> memberConsumptions
                = memberConsumptionRepository.findMemberConsumptionByKakaoId(kakaoId);

        return memberConsumptions.stream()
                .map(consumption -> MemberConsumptionResponseDto.builder()
                        .companyType(consumption.getCompanyType())
                        .totalPrice(consumption.getTotalPrice())
                        .build()).toList();
    }

    @Override
    public List<MemberConsumptionResponseDto> findConsumptionByMemberAndCompanyType(CompanyType companyType) {
        Long kakaoId = SecurityUtil.getCurrentMemberKakaoId();
        List<MemberConsumption> memberConsumptions = memberConsumptionRepository
                .findConsumptionByKakaoIdAndCompanyType(kakaoId, companyType);

        return memberConsumptions.stream()
                .map(consumption -> MemberConsumptionResponseDto.builder()
                        .companyType(consumption.getCompanyType())
                        .totalPrice(consumption.getTotalPrice())
                        .build()).toList();
    }


}
