package com.jb.fop.repository;

import com.jb.fop.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDetailsRepo extends JpaRepository<UserDetails, Integer> {
    public UserDetails findByEmail(String email);
}
