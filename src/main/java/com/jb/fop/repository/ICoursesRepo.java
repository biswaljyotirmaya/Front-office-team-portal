package com.jb.fop.repository;

import com.jb.fop.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICoursesRepo extends JpaRepository<Courses, Integer> {
}
