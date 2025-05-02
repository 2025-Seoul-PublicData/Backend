package com.example.seoulpublicdata2025backend.domain.company.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CompanyCategoryConverter implements AttributeConverter<CompanyCategory, String> {

    @Override
    public String convertToDatabaseColumn(CompanyCategory attribute) {
        return attribute != null ? attribute.getKoreanName() : null;
    }

    @Override
    public CompanyCategory convertToEntityAttribute(String dbData) {
        return dbData != null ? CompanyCategory.fromKorean(dbData) : null;
    }
}
