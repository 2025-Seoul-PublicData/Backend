package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.company.dao.MemberCompanySaveRepository;
import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.company.entity.MemberCompanySave;
import com.example.seoulpublicdata2025backend.domain.member.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.global.exception.customException.MemberCompanySaveException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(MemberCompanySaveServiceImpl.class)
class MemberCompanySaveServiceImplTest {

    @Autowired
    private MemberCompanySaveService memberCompanySaveService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MemberCompanySaveRepository memberCompanySaveRepository;

    @Autowired
    private EntityManager entityManager;

    private Member testMember;
    private Company testCompany1;
    private Company testCompany2;

    @BeforeEach
    void setUp() {
        // Member 생성
        testMember = Member.builder()
                .kakaoId(1001L)
                .name("홍길동")
                .location("서울")
                .role(Member.Role.CONSUMER)
                .build();
        entityManager.persist(testMember);

        // Company 2개 생성
        testCompany1 = Company.builder()
                .companyId(1L)
                .companyName("테스트 기업1")
                .companyLocation("서울 강남구")
                .companyCategory(null)
                .companyType(null)
                .business("소셜벤처")
                .build();
        entityManager.persist(testCompany1);

        testCompany2 = Company.builder()
                .companyId(2L)
                .companyName("테스트 기업2")
                .companyLocation("서울 마포구")
                .companyCategory(null)
                .companyType(null)
                .business("협동조합")
                .build();
        entityManager.persist(testCompany2);

        // Authentication 세팅
        setAuthentication(1001L);
    }

    private void setAuthentication(Long kakaoId) {
        User user = new User(String.valueOf(kakaoId), "", List.of());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void 기업_찜_성공() {
        memberCompanySaveService.saveCompany(testCompany1.getCompanyId());

        List<MemberCompanySave> saves = memberCompanySaveRepository.findByKakaoId(testMember.getKakaoId());

        assertEquals(1, saves.size());
        assertEquals(testCompany1.getCompanyId(), saves.get(0).getCompany().getCompanyId());
    }

    @Test
    void 중복_찜_예외() {
        memberCompanySaveService.saveCompany(testCompany1.getCompanyId());

        MemberCompanySaveException exception = assertThrows(MemberCompanySaveException.class,
                () -> memberCompanySaveService.saveCompany(testCompany1.getCompanyId()));
        assertEquals("이미 찜한 기업입니다.", exception.getMessage());
    }

    @Test
    void 찜_취소_성공() {
        memberCompanySaveService.saveCompany(testCompany1.getCompanyId());
        memberCompanySaveService.unSaveCompany(testCompany1.getCompanyId());

        List<MemberCompanySave> saves = memberCompanySaveRepository.findByKakaoId(testMember.getKakaoId());
        assertEquals(0, saves.size());
    }

    @Test
    void 찜_취소시_기록없으면_예외() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> memberCompanySaveService.unSaveCompany(testCompany1.getCompanyId()));
        assertEquals("찜한 기록이 없습니다.", exception.getMessage());
    }

    @Test
    void 내가_찜한_기업_목록_조회() {
        memberCompanySaveService.saveCompany(testCompany1.getCompanyId());
        memberCompanySaveService.saveCompany(testCompany2.getCompanyId());

        var savedCompanies = memberCompanySaveService.getSavedCompanies();
        assertEquals(2, savedCompanies.size());
    }

    @Test
    void 내가_찜한_기업_개수_조회() {
        memberCompanySaveService.saveCompany(testCompany1.getCompanyId());
        memberCompanySaveService.saveCompany(testCompany2.getCompanyId());

        Long count = memberCompanySaveService.countSavedByMember();
        assertEquals(2L, count);
    }
}
