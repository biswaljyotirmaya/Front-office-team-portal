package com.jb.fop.runner;

import com.jb.fop.entity.Courses;
import com.jb.fop.entity.InquiryDetails;
import com.jb.fop.entity.Status;
import com.jb.fop.repository.ICoursesRepo;
import com.jb.fop.repository.IStatusRepo;
import com.jb.fop.repository.InquiryRepo;
import org.hibernate.annotations.AttributeAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ICoursesRepo coursesRepo;

    @Autowired
    private InquiryRepo inquiryRepo;
    @Autowired
    private IStatusRepo iStatusRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        coursesRepo.deleteAll();
        iStatusRepo.deleteAll();

        Courses c1 = new Courses();
        c1.setCoursesName("Java");
        Courses c2 = new Courses();
        c2.setCoursesName("Python");
        Courses c3 = new Courses();
        c3.setCoursesName("JavaScript");
        Courses c4 = new Courses();
        c4.setCoursesName("MySQL");
        Courses c5 = new Courses();
        c5.setCoursesName("C++");
        coursesRepo.saveAll(Arrays.asList(c1, c2, c3, c4, c5));

        Status s1 = new Status();
        s1.setStatusName("New");
        Status s2 = new Status();
        s2.setStatusName("Enrolled");
        Status s3 = new Status();
        s3.setStatusName("Lost");
        iStatusRepo.saveAll(Arrays.asList(s1, s2, s3));

    }
}
