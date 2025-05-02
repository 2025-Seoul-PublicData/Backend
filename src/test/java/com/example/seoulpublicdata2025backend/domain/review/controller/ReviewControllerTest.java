package com.example.seoulpublicdata2025backend.domain.review.controller;

import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.entity.ReviewCategory;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@Import(ReviewControllerTest.MockConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtParser jwtParser;

    @TestConfiguration
    static class MockConfig {
        private static final JwtParser jwtParser = Mockito.mock(JwtParser.class);
        private static final ReviewService reviewService = Mockito.mock(ReviewService.class);

        @Bean
        public JwtParser jwtParser() {
            return jwtParser;
        }

        @Bean
        public ReviewService reviewService() {
            return reviewService;
        }
    }

    @Test
    @DisplayName("리뷰 생성 요청 테스트")
    void createReview() throws Exception {
        when(MockConfig.jwtParser.validationToken("1001")).thenReturn(true);
        when(MockConfig.jwtParser.getAuthentication("1001"))
                .thenReturn(new UsernamePasswordAuthenticationToken("1001", null, List.of()));

        CompanyReviewDto requestDto = new CompanyReviewDto(
                null,
                1L,
                LocalDateTime.of(2025, 4, 25, 14, 30),
                Company.builder().companyId(1L).companyName("테스트회사").build(),
                Member.builder().kakaoId(1001L).name("홍길동").build(),
                "친절하고 깨끗했어요!",
                89.5,
                Set.of(ReviewCategory.CLEAN, ReviewCategory.REVISIT)
        );

        CompanyReviewDto responseDto = new CompanyReviewDto(
                100L,
                1L,
                LocalDateTime.of(2025, 4, 25, 14, 30),
                requestDto.getCompany(),
                requestDto.getKakao(),
                requestDto.getReview(),
                requestDto.getTemperature(),
                requestDto.getReviewCategories()
        );

        when(MockConfig.reviewService.creatCompanyReview(any())).thenReturn(responseDto);

        mockMvc.perform(post("/reviews/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .cookie(new Cookie("accessToken", "1001"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.review").value("친절하고 깨끗했어요!"))
                .andExpect(jsonPath("$.temperature").value(89.5))
                .andExpect(jsonPath("$.reviewId").value(100));
    }

    @Test
    @WithMockUser
    @DisplayName("회사 리뷰 전체 조회 테스트")
    void getAllCompanyReviews() throws Exception {
        when(reviewService.getAllCompanyReviews(1L)).thenReturn(
                List.of(new ReviewDto(
                        1L, 1001L, "전재학", "Gray",
                        "좋아요!", 88.5,
                        Set.of(ReviewCategory.CLEAN, ReviewCategory.REVISIT)
                ))
        );

        mockMvc.perform(get("/reviews/public/get-all-company-reviews")
                        .param("companyId", "1")
                        .cookie(new Cookie("accessToken", "1001")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reviewContent").value("좋아요!"))
                .andExpect(jsonPath("$[0].reviewCategories", hasItems("CLEAN", "REVISIT")));
    }

    @Test
    @DisplayName("리뷰 수정 요청 테스트")
    void updateReview() throws Exception {
        CompanyReviewDto requestDto = new CompanyReviewDto(
                1L,
                12345L,
                LocalDateTime.of(2025, 5, 2, 10, 30),
                Company.builder().companyId(1L).companyName("수정된 회사").build(),
                Member.builder().kakaoId(1001L).name("홍길동").build(),
                "수정된 리뷰입니다.",
                77.7,
                Set.of(ReviewCategory.CLEAN, ReviewCategory.REASONABLE_PRICE)
        );

        when(MockConfig.jwtParser.validationToken("1001")).thenReturn(true);
        when(MockConfig.jwtParser.getAuthentication("1001"))
                .thenReturn(new UsernamePasswordAuthenticationToken("1001", null, List.of()));

        when(MockConfig.reviewService.updateCompanyReview(any(), any())).thenReturn(requestDto);

        mockMvc.perform(put("/reviews/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .cookie(new Cookie("accessToken", "1001"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.review").value("수정된 리뷰입니다."))
                .andExpect(jsonPath("$.temperature").value(77.7));
    }

    @Test
    @DisplayName("리뷰 삭제 요청 테스트")
    void deleteReview() throws Exception {
        CompanyReviewDto requestDto = new CompanyReviewDto(
                1L,
                12345L,
                LocalDateTime.of(2025, 5, 2, 10, 30),
                Company.builder().companyId(1L).companyName("삭제 대상 회사").build(),
                Member.builder().kakaoId(1001L).name("홍길동").build(),
                "삭제할 리뷰입니다.",
                50.0,
                Set.of(ReviewCategory.CLEAN)
        );

        when(MockConfig.jwtParser.validationToken("1001")).thenReturn(true);
        when(MockConfig.jwtParser.getAuthentication("1001"))
                .thenReturn(new UsernamePasswordAuthenticationToken("1001", null, List.of()));

        when(MockConfig.reviewService.deleteCompanyReview(any())).thenReturn(requestDto);

        mockMvc.perform(delete("/reviews/1")
                        .cookie(new Cookie("accessToken", "1001"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.review").value("삭제할 리뷰입니다."))
                .andExpect(jsonPath("$.temperature").value(50.0));
    }
}
