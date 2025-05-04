package com.example.seoulpublicdata2025backend.domain.support.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "support_announcements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SupportAnnouncement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String organization;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "announcement_type")
    private String announcementType;

    @Lob    // MySQL에서 text로 DDL이 돼있어서 이 애노테이션을 사용했습니다.
    private String summary;

    @Lob
    private String link;

    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp createdAt;
}
