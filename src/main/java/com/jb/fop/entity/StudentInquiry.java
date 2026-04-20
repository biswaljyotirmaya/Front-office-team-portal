package com.jb.fop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class StudentInquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inquiryId;
    private String StudentName;
    private Long PhoneNumber;
    private String ClassMode;
    private String CourseName;
    private String inquiryStatus;
    private LocalDate createdDate;
    private LocalDate updatedDate;

//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private Integer UserId;
}
