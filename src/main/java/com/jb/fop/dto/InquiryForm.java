package com.jb.fop.dto;

import lombok.Data;

@Data
public class InquiryForm {
    private Integer inquiryId;
    private String studentName;
    private String phoneNumber;
    private String classMode;
    private String courseName;
    private String inquiryStatus;
}
