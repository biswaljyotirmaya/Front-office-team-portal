package com.jb.fop.service;

import com.jb.fop.dto.DashboardResponse;
import com.jb.fop.dto.InquiryForm;
import com.jb.fop.dto.InquirySearchCriteria;

import java.util.List;

public interface IInquiryService {

    public List<String> getCoursesName();

    public List<String> getInquiryStatus();

    public DashboardResponse getDashboard(Integer userId);

    public String getInquiry(InquiryForm inquiryForm);

    public List<InquiryForm> getInquiryList(Integer userId, String course, String status, String mode);

    public InquiryForm getInquiryById(Integer inquiryId);

    public String addInquiry(InquiryForm inquiryForm);
}
