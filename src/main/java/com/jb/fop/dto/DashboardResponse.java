package com.jb.fop.dto;

import lombok.Data;

@Data
public class DashboardResponse {
    private long totalInquiryCount;
    private long enrolledCount;
    private long lostCount;
}
