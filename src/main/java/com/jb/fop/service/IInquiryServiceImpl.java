package com.jb.fop.service;

import com.jb.fop.dto.DashboardResponse;
import com.jb.fop.dto.InquiryForm;
import com.jb.fop.dto.InquirySearchCriteria;
import com.jb.fop.entity.InquiryDetails;
import com.jb.fop.entity.UserDetails;
import com.jb.fop.repository.IUserDetailsRepo;
import com.jb.fop.repository.InquiryRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IInquiryServiceImpl implements IInquiryService {

    @Autowired
    private IUserDetailsRepo userDetailsRepo;

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private InquiryRepo inquiryRepo;

    @Override
    public List<String> getCoursesName() {
        return List.of();
    }

    @Override
    public List<String> getInquiryStatus() {
        return List.of();
    }

    @Override
    public DashboardResponse getDashboard(Integer userId) {

        List<InquiryDetails> inquiryList = userDetailsRepo.getInquiryDetailsByUserId(userId);

        if (inquiryList == null || inquiryList.isEmpty()) {
            return new DashboardResponse();
        }
        int total = 0;
        long enrolled = 0;
        long lost = 0;

        for (InquiryDetails e : inquiryList) {
            total++;

            String status = e.getInquiryStatus();

            if ("ENROLLED".equalsIgnoreCase(status)) {
                enrolled++;
            } else if ("LOST".equalsIgnoreCase(status)) {
                lost++;
            }
        }

        DashboardResponse res = new DashboardResponse();
        res.setTotalInquiryCount(total);
        res.setEnrolledCount(enrolled);
        res.setLostCount(lost);

        return res;
    }

    @Override
    public String getInquiry(InquiryForm inquiryForm) {
        return "";
    }

    @Override
    public List<InquiryForm> getInquiryList(Integer userId, String course, String status, String mode) {
        InquiryDetails inquiryDetails = new InquiryDetails();

        if (course != null && !course.isBlank()) {
            inquiryDetails.setCourseName(course);
        }
        if (status != null && !status.isBlank()) {
            inquiryDetails.setClassMode(status);
        }

        if (mode != null && !mode.isBlank()) {
            inquiryDetails.setInquiryStatus(mode);
        }

        UserDetails user = userDetailsRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        inquiryDetails.setUser(user);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase();

        Example<InquiryDetails> example = Example.of(inquiryDetails, matcher);

        List<InquiryDetails> filteredData = inquiryRepo.findAll(example);
        List<InquiryForm> inquiryFormList = new ArrayList<>();
        for (InquiryDetails inquiryDetail : filteredData) {
            InquiryForm inquiryForm = new InquiryForm();
            BeanUtils.copyProperties(inquiryDetail, inquiryForm);
            inquiryFormList.add(inquiryForm);
        }
        return inquiryFormList;
    }

    @Override
    public InquiryForm getInquiryById(Integer inquiryId) {
        return null;
    }

    @Override
    public String addInquiry(InquiryForm inquiryForm) {
        System.out.println("inquiryForm = " + inquiryForm);
        InquiryDetails inquiryDetails = new InquiryDetails();
        BeanUtils.copyProperties(inquiryForm, inquiryDetails);
        if (inquiryForm.getStudentPhoneNumber() != null) {
            inquiryDetails.setPhoneNumber(Long.parseLong(inquiryForm.getStudentPhoneNumber()));
        }

        Object userObj = httpSession.getAttribute("userID");
        if (userObj == null) {
            return "ERROR: Session expired. Please login again.";
        }

        Integer userId = (Integer) userObj;
        UserDetails user = userDetailsRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        inquiryDetails.setUser(user);
        inquiryDetails.setCreatedDate(LocalDate.now());
        inquiryDetails.setUpdatedDate(LocalDate.now());

        inquiryRepo.save(inquiryDetails);
        return "SUCCESS: Inquiry added successfully";
    }
}
