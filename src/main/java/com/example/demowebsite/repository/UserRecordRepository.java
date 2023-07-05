package com.example.demowebsite.repository;

import com.example.demowebsite.model.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {
    boolean existsByEmail(String email);
}