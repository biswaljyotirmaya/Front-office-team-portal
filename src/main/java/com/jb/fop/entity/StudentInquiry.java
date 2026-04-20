package com.jb.fop.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class StudentInquiry {
    private Integer inquiryId;
    private String StudentName;
    private Long PhoneNumber;
    private String ClassMode;
    private String CourseName;
    private String inquiryStatus;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Integer UserId;
}
