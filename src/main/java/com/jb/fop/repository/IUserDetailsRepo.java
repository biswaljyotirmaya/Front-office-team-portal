package com.jb.fop.repository;

import com.jb.fop.entity.InquiryDetails;
import com.jb.fop.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserDetailsRepo extends JpaRepository<UserDetails, Integer> {
    UserDetails findByEmail(String email);

    @Query("SELECT i FROM InquiryDetails i WHERE i.user.userId = :userId")
    List<InquiryDetails> getInquiryDetailsByUserId(@Param("userId")Integer userId);
}
