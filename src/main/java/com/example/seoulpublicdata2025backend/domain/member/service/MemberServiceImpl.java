package com.example.seoulpublicdata2025backend.domain.member.service;

import com.example.seoulpublicdata2025backend.domain.member.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.member.dto.AuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.KakaoIdStatusDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.UpdateMemberRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.UpdateMemberResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundMemberException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public KakaoIdStatusDto initMember(Long kakaoId) {
        // member 를 찾고 이미 가입된 member 라면 init을 하지 않는다.
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> {
                    Member newMember = Member.init(kakaoId);
                    return memberRepository.save(newMember);
                });

        return new KakaoIdStatusDto(member.getKakaoId(), member.getStatus());
    }

    @Override
    @Transactional
    public SignupResponseDto completeSignup(SignupRequestDto dto) {
        Long kakaoId = SecurityUtil.getCurrentMemberKakaoId();
        Member findMember = memberRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
        findMember.completeSignup(dto);
        return SignupResponseDto.from(kakaoId, findMember.getStatus());
    }

    @Override
    public Optional<Member> findByKakaoId(Long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId);
    }

    @Override
    public AuthResponseDto getMemberAuth() {
        Long kakaoId = SecurityUtil.getCurrentMemberKakaoId();
        return memberRepository.findAuthResponseByKakaoId(kakaoId).orElseThrow(
                () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
    }


    @Override
    public String findMemberName() {
        Long kakaoId = SecurityUtil.getCurrentMemberKakaoId();
        return memberRepository.findMemberName(kakaoId).orElseThrow(
                () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    @Transactional
    public UpdateMemberResponseDto updateMember(UpdateMemberRequestDto dto) {
        Member findMember = memberRepository.findByKakaoId(SecurityUtil.getCurrentMemberKakaoId()).orElseThrow(
                () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
        findMember.update(dto);
        return UpdateMemberResponseDto.builder()
                .name(findMember.getName())
                .location(findMember.getLocation())
                .profileColor(findMember.getProfileColor())
                .build();
    }
}
