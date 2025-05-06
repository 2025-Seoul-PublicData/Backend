package com.example.seoulpublicdata2025backend.domain.support.service;

import com.example.seoulpublicdata2025backend.domain.support.dao.MemberSupportProductRepository;
import com.example.seoulpublicdata2025backend.domain.support.entity.MemberSupportProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberSupportProductServiceImpl implements MemberSupportProductService {

    private final MemberSupportProductRepository memberSupportProductRepository;

    @Override
    public List<MemberSupportProduct> getMemberSupportProducts() {
        return memberSupportProductRepository.findAll();
    }
}
