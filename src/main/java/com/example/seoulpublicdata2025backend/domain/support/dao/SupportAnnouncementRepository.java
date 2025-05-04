package com.example.seoulpublicdata2025backend.domain.support.dao;

import com.example.seoulpublicdata2025backend.domain.support.entity.SupportAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportAnnouncementRepository extends JpaRepository<SupportAnnouncement, Integer> {

}
