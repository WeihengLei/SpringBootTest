package com.example.test.dao;

import com.example.test.entity.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestUserRepository extends JpaRepository<TestUser, Long> {
    TestUser findById(long id);
    Long deleteById(Long id);
}