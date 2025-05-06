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
    @DisplayName("소비자 지원 상품 전체 조회 성공")
    void getMemberSupportProducts_success() {
        // given
        List<ConsumerSupportProduct> products = List.of(
                ConsumerSupportProduct.builder()
                        .bankName("카카오뱅크")
                        .productName("청년 지원 적금")
                        .productDescription("청년을 위한 고금리 적금 상품")
                        .productType("적금")
                        .benefit("금리 우대")
                        .build(),
                ConsumerSupportProduct.builder()
                        .bankName("신한은행")
                        .productName("사회적 기업 대출")
                        .productDescription("사회적 기업을 위한 저금리 대출 상품")
                        .productType("대출")
                        .benefit("이자 지원")
                        .build()
        );

        when(consumerSupportProductRepository.findAll()).thenReturn(products);

        // when
        List<ConsumerSupportProductResponseDto> result = consumerSupportProductService.getMemberSupportProducts();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getBankName()).isEqualTo("카카오뱅크");
        assertThat(result.get(1).getProductName()).isEqualTo("사회적 기업 대출");
    }

    @Test
    @DisplayName("하나의 소비자 지원 상품 조회 성공")
    void get() throws Exception {
        ConsumerSupportProduct supportProduct = ConsumerSupportProduct.builder()
                .bankName("신한은행")
                .productName("사회적 기업 대출")
                .productDescription("사회적 기업을 위한 저금리 대출 상품")
                .productType("대출")
                .benefit("이자 지원")
                .productLink("www.link.com")
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
    }
}