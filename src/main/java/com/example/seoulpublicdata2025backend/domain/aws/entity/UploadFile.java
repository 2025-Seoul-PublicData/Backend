package com.example.seoulpublicdata2025backend.domain.aws.entity;

import com.example.seoulpublicdata2025backend.domain.aws.dto.UploadType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "upload_files")
public class UploadFile {
    @Id
    @Column(name = "upload_file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long kakaoId;

    @Column(nullable = false)
    private String objectKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UploadType type; // REVIEW, ETC

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public UploadFile(Long kakaoId, String objectKey, UploadType type, String originalFileName) {
        this.kakaoId = kakaoId;
        this.objectKey = objectKey;
        this.type = type;
        this.originalFileName = originalFileName;
        this.createdAt = LocalDateTime.now();
    }
}
