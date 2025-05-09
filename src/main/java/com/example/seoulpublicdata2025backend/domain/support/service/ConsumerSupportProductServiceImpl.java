package com.example.seoulpublicdata2025backend.domain.support.service;

import com.example.seoulpublicdata2025backend.domain.support.dao.ConsumerSupportProductRepository;
import com.example.seoulpublicdata2025backend.domain.support.dto.ConsumerSupportProductResponseDetailDto;
import com.example.seoulpublicdata2025backend.domain.support.dto.ConsumerSupportProductResponseDto;
import com.example.seoulpublicdata2025backend.domain.support.entity.ConsumerSupportProduct;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundSupportException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ConsumerSupportProductServiceImpl implements ConsumerSupportProductService {

    private final ConsumerSupportProductRepository consumerSupportProductRepository;

    @Override
    @Transactional
    public List<ConsumerSupportProductResponseDto> getMemberSupportProducts() {
        List<ConsumerSupportProduct> consumerSupportProducts = consumerSupportProductRepository.findAll();
        return consumerSupportProducts.stream()
                .map(support -> ConsumerSupportProductResponseDto.builder()
                        .id(support.getId())
                        .bankName(support.getBankName())
                        .productName(support.getProductName())
                        .productDescription(support.getProductDescription())
                        .productType(support.getProductType())
                        .benefit(support.getBenefit())
                        .defaultCategory(support.getDefaultCategory())
                        .recommendedCategory(support.getRecommendedCategory())
                        .build()
                ).toList();
    }

    @Override
    public ConsumerSupportProductResponseDetailDto getMemberSupportProductDetail(Integer productId) {
        ConsumerSupportProduct support = consumerSupportProductRepository.findById(Long.valueOf(productId))
                .orElseThrow(() -> new NotFoundSupportException(ErrorCode.CONSUMER_SUPPORT_NOT_FOUND));
        return ConsumerSupportProductResponseDetailDto.builder()
                .bankName(support.getBankName())
                .productName(support.getProductName())
                .productDescription(support.getProductDescription())
                .productType(support.getProductType())
                .benefit(support.getBenefit())
                .productLink(support.getProductLink())
                .method(support.getMethod())
                .period(support.getPeriod())
                .defaultCategory(support.getDefaultCategory())
                .recommendedCategory(support.getRecommendedCategory())
                .build();
    }
}
