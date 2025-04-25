package com.example.seoulpublicdata2025backend.domain.review.controller;

import com.example.seoulpublicdata2025backend.domain.geocoding.entity.Company;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.entity.ReviewCategory;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        // when - Jwt 인증 설정
        when(MockConfig.jwtParser.validationToken("1001")).thenReturn(true);
        when(MockConfig.jwtParser.getAuthentication("1001"))
                .thenReturn(new UsernamePasswordAuthenticationToken("1001", null, List.of()));

        // given - DTO 구성
        CompanyReviewDto requestDto = new CompanyReviewDto(
                1L,
                LocalDateTime.of(2025, 4, 25, 14, 30),
                Company.builder().companyId(1L).companyName("테스트회사").build(),
                Member.builder().kakaoId(1001L).name("홍길동").build(),
                "친절하고 깨끗했어요!",
                89.5,
                ReviewCategory.CLEAN
        );

        when(MockConfig.reviewService.creatCompanyReview(any())).thenReturn(requestDto);

        // then
        mockMvc.perform(post("/reviews/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .cookie(new Cookie("accessToken", "1001"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.review").value("친절하고 깨끗했어요!"))
                .andExpect(jsonPath("$.temperature").value(89.5));
    }

    @Test
    @WithMockUser
    @DisplayName("회사 리뷰 전체 조회 테스트")
    void getAllCompanyReviews() throws Exception {
        //Given
        when(reviewService.getAllCompanyReviews(1L)).thenReturn(
                List.of(new ReviewDto(1L, 1001L, "좋아요!", 88.5))
        );

        // when & then
        mockMvc.perform(get("/reviews/public/get-all-company-reviews")
                        .param("companyId", "1")
                        .cookie(new Cookie("accessToken", "1001")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reviewContent").value("좋아요!"));
}

}