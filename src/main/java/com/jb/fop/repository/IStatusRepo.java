package com.jb.fop.repository;

import com.jb.fop.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatusRepo extends JpaRepository<Status, Integer> {
}
