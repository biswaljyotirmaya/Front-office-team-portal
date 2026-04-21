package com.jb.fop.dto;

import lombok.Data;

@Data
public class DashboardResponse {
    private Integer totalInquiryCount;
    private Integer enrolledCount;
    private Integer lostCount;
}
