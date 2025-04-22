package com.example.seoulpublicdata2025backend.global.auth.springsecurity;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundMemberException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByKakaoId(Long.parseLong(username)).orElseThrow(
                () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
        return new CustomUserDetails(findMember);
    }
}
