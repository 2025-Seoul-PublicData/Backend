package com.example.seoulpublicdata2025backend.domain.aws.dao;

import com.example.seoulpublicdata2025backend.domain.aws.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, String> {
}
