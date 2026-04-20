package com.jb.fop.repository;

import com.jb.fop.entity.StudentInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentInquiryRepo extends JpaRepository<StudentInquiry, Integer> {
}
