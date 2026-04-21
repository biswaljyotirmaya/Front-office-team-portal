package com.jb.fop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class StudentInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inquiryId;

    private String studentName;
    private Long phoneNumber;
    private String classMode;
    private String courseName;
    private String inquiryStatus;

    private LocalDate createdDate;
    private LocalDate updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDetails user;
}