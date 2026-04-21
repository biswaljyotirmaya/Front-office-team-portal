package com.jb.fop.service;

import com.jb.fop.dto.DashboardResponse;
import com.jb.fop.dto.InquiryForm;
import com.jb.fop.dto.InquirySearchCriteria;

import java.util.List;

public class IInquiryServiceImpl  implements IInquiryService {


    @Override
    public List<String> getCoursesName() {
        return List.of();
    }

    @Override
    public List<String> getInquiryStatus() {
        return List.of();
    }

    @Override
    public DashboardResponse getDashboard() {
        return null;
    }

    @Override
    public String getInquiry(InquiryForm inquiryForm) {
        return "";
    }

    @Override
    public List<InquiryForm> getInquiryList(Integer userId, InquirySearchCriteria inquirySearchCriteria) {
        return List.of();
    }

    @Override
    public InquiryForm getInquiryById(Integer inquiryId) {
        return null;
    }
}
