package com.jb.fop.repository;

import com.jb.fop.entity.InquiryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepo extends JpaRepository<InquiryDetails, Integer> {
}
