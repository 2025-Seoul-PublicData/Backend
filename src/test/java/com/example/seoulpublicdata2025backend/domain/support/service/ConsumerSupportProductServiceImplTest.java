package com.example.seoulpublicdata2025backend.domain.support.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.seoulpublicdata2025backend.domain.support.dao.ConsumerSupportProductRepository;
import com.example.seoulpublicdata2025backend.domain.support.dto.ConsumerSupportProductResponseDetailDto;
import com.example.seoulpublicdata2025backend.domain.support.dto.ConsumerSupportProductResponseDto;
import com.example.seoulpublicdata2025backend.domain.support.entity.ConsumerSupportProduct;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsumerSupportProductServiceImplTest {

    @Mock
    private ConsumerSupportProductRepository consumerSupportProductRepository;

    @InjectMocks
    ConsumerSupportProductServiceImpl consumerSupportProductService;

    @Test
    @DisplayName("소비자 지원 상품 전체 조회 성공 - 모든 필드 검증")
    void getMemberSupportProducts_success() {
        // given
        List<ConsumerSupportProduct> products = List.of(
                ConsumerSupportProduct.builder()
                        .bankName("카카오뱅크")
                        .productName("청년 지원 적금")
                        .productDescription("청년을 위한 고금리 적금 상품")
                        .productType("적금")
                        .benefit("금리 우대")
                        .productLink("https://kakaobank.com")
                        .method("온라인 신청")
                        .period("2025년 상반기")
                        .defaultCategory("기본 금융지원")
                        .recommendedCategory("사회적경제 추천")
                        .build(),
                ConsumerSupportProduct.builder()
                        .bankName("신한은행")
                        .productName("사회적 기업 대출")
                        .productDescription("사회적 기업을 위한 저금리 대출 상품")
                        .productType("대출")
                        .benefit("이자 지원")
                        .productLink("https://shinhan.com")
                        .method("지점 방문")
                        .period("상시 모집")
                        .defaultCategory("기본 금융지원")
                        .recommendedCategory("사회적경제 추천")
                        .build()
        );

        when(consumerSupportProductRepository.findAll()).thenReturn(products);

        // when
        List<ConsumerSupportProductResponseDto> result = consumerSupportProductService.getMemberSupportProducts();

        // then
        assertThat(result).hasSize(2);

        ConsumerSupportProductResponseDto first = result.get(0);
        assertThat(first.getBankName()).isEqualTo("카카오뱅크");
        assertThat(first.getProductName()).isEqualTo("청년 지원 적금");
        assertThat(first.getProductDescription()).isEqualTo("청년을 위한 고금리 적금 상품");
        assertThat(first.getProductType()).isEqualTo("적금");
        assertThat(first.getBenefit()).isEqualTo("금리 우대");
        assertThat(first.getDefaultCategory()).isEqualTo("기본 금융지원");
        assertThat(first.getRecommendedCategory()).isEqualTo("사회적경제 추천");

        ConsumerSupportProductResponseDto second = result.get(1);
        assertThat(second.getBankName()).isEqualTo("신한은행");
        assertThat(second.getProductName()).isEqualTo("사회적 기업 대출");
        assertThat(second.getProductDescription()).isEqualTo("사회적 기업을 위한 저금리 대출 상품");
        assertThat(second.getProductType()).isEqualTo("대출");
        assertThat(second.getBenefit()).isEqualTo("이자 지원");
        assertThat(second.getDefaultCategory()).isEqualTo("기본 금융지원");
        assertThat(second.getRecommendedCategory()).isEqualTo("사회적경제 추천");
    }


    @Test
    @DisplayName("하나의 소비자 지원 상품 조회 성공 - 모든 필드 확인")
    void get() throws Exception {
        ConsumerSupportProduct supportProduct = ConsumerSupportProduct.builder()
                .bankName("신한은행")
                .productName("사회적 기업 대출")
                .productDescription("사회적 기업을 위한 저금리 대출 상품")
                .productType("대출")
                .benefit("이자 지원")
                .productLink("www.link.com")
                .method("온라인 신청")
                .period("2025년 상반기")
                .defaultCategory("기본 금융지원")
                .recommendedCategory("사회적경제 추천")
                .build();

        when(consumerSupportProductRepository.findById(-1L)).thenReturn(Optional.of(supportProduct));

        ConsumerSupportProductResponseDetailDto productDetail
                = consumerSupportProductService.getMemberSupportProductDetail(-1);

        assertThat(productDetail.getBankName()).isEqualTo("신한은행");
        assertThat(productDetail.getProductName()).isEqualTo("사회적 기업 대출");
        assertThat(productDetail.getProductDescription()).isEqualTo("사회적 기업을 위한 저금리 대출 상품");
        assertThat(productDetail.getProductType()).isEqualTo("대출");
        assertThat(productDetail.getBenefit()).isEqualTo("이자 지원");
        assertThat(productDetail.getProductLink()).isEqualTo("www.link.com");
        assertThat(productDetail.getMethod()).isEqualTo("온라인 신청");
        assertThat(productDetail.getPeriod()).isEqualTo("2025년 상반기");
        assertThat(productDetail.getDefaultCategory()).isEqualTo("기본 금융지원");
        assertThat(productDetail.getRecommendedCategory()).isEqualTo("사회적경제 추천");
    }

}